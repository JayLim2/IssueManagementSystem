package org.sergei.komarov.controllers;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.ProjectType;
import org.sergei.komarov.services.ProjectTypesService;
import org.sergei.komarov.services.ProjectsService;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectsController {

    private final ProjectsService projectsService;
    private final ProjectTypesService projectTypesService;

    @PostMapping("/add")
    public Map<String, Object> addProject(String title, @RequestParam int projectTypeId) {

        Map<String, Object> attrs = new HashMap<>();
        List<ProjectType> projectTypes = projectTypesService.getAll();
        //attrs.put("projectTypes", projectTypes);
        ProjectType projectType = projectTypesService.getById(projectTypeId);
        projectsService.validateAndSave(attrs, title, projectType);

        return attrs;
    }

    @PostMapping("/edit")
    public Map<String, Object> editProject(int id,
                                           String title, @RequestParam int projectTypeId) {

        Map<String, Object> attrs = new HashMap<>();
        List<ProjectType> projectTypes = projectTypesService.getAll();
        //attrs.put("projectTypes", projectTypes);
        ProjectType projectType = projectTypesService.getById(projectTypeId);
        projectsService.validateAndUpdate(attrs, id, title, projectType);

        return attrs;
    }

    @PostMapping("/delete")
    public Map<String, Object> deleteProject(int id) {
        Map<String, Object> attrs = new HashMap<>();

        boolean isExists = projectsService.isExistsById(id);
        attrs.put("isExists", isExists);
        String message = null;
        if (!isExists) {
            message = "Проект с таким ID не существует.";
        } else {
            try {
                projectsService.deleteById(id);
            } catch (TransactionSystemException e) {
                Throwable ex = SQLExceptionParser.getUnwrappedPSQLException(e);
                if (ex != null) {
                    ex.printStackTrace();
                    message = ex.getMessage();
                }
            }
        }

        if (message == null) {
            attrs.put("info", "Проект с ID " + id + " удален.");
        } else {
            attrs.put("error", message);
        }

        return attrs;
    }
}
