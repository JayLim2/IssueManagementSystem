package org.sergei.komarov.controllers;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.Project;
import org.sergei.komarov.models.ProjectType;
import org.sergei.komarov.services.ProjectTypesService;
import org.sergei.komarov.services.ProjectsService;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectsController {

    private final ProjectsService projectsService;
    private final ProjectTypesService projectTypesService;

    @RequestMapping("/view")
    public String getProjects(Model model) {
        List<Project> projects = projectsService.getAll();

        model.addAttribute("projects", projects);

        return "projects";
    }

    @RequestMapping("/view/{projectId}")
    public String getProject(Model model, @PathVariable int projectId) {
        Project project = projectsService.getById(projectId);

        model.addAttribute("project", project);

        return "project";
    }

    @GetMapping("/add")
    public String addProject(Model model) {
        List<ProjectType> projectTypes = projectTypesService.getAll();
        model.addAttribute("projectTypes", projectTypes);
        return "addProject";
    }

    @PostMapping("/add")
    public String addProject(Model model, String title, int projectTypeId) {
        Map<String, Object> attrs = new HashMap<>();
        List<ProjectType> projectTypes = projectTypesService.getAll();
        attrs.put("projectTypes", projectTypes);
        ProjectType projectType = projectTypesService.getById(projectTypeId);
        projectsService.validateAndSave(attrs, title, projectType);
        model.addAllAttributes(attrs);

        return "addProject";
    }

    @GetMapping("/edit/{projectId}")
    public String editProject(Model model, @PathVariable int projectId) {
        Map<String, Object> attrs = new HashMap<>();

        if (!projectsService.isExistsById(projectId)) {
            attrs.put("error", "Проект с таким ID не существует.");
        } else {
            Project project = projectsService.getById(projectId);
            attrs.put("entity", project);
            List<ProjectType> projectTypes = projectTypesService.getAll();
            attrs.put("projectTypes", projectTypes);
        }
        model.addAllAttributes(attrs);

        return "editProject";
    }

    @PostMapping("/edit/{projectId}")
    public String editProject(Model model, @PathVariable int projectId,
                              String title, int projectTypeId) {

        Map<String, Object> attrs = new HashMap<>();
        List<ProjectType> projectTypes = projectTypesService.getAll();
        attrs.put("projectTypes", projectTypes);
        ProjectType projectType = projectTypesService.getById(projectTypeId);
        projectsService.validateAndUpdate(attrs, projectId, title, projectType);
        model.addAllAttributes(attrs);

        return "editProject";
    }

    @PostMapping("/delete/{projectId}")
    public String deleteProject(Model model, @PathVariable int projectId) {
        Map<String, Object> attrs = new HashMap<>();

        boolean isExists = projectsService.isExistsById(projectId);
        attrs.put("isExists", isExists);
        String message = null;
        if (!isExists) {
            message = "Проект с таким ID не существует.";
        } else {
            try {
                projectsService.deleteById(projectId);
            } catch (TransactionSystemException e) {
                Throwable ex = SQLExceptionParser.getUnwrappedPSQLException(e);
                if (ex != null) {
                    ex.printStackTrace();
                    message = ex.getMessage();
                }
            }
        }

        if (message == null) {
            attrs.put("info", "Проект с ID " + projectId + " удален.");
        } else {
            attrs.put("error", message);
        }

        model.addAllAttributes(attrs);

        return "delete";
    }
}
