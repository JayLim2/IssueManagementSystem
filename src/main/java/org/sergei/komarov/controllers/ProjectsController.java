package org.sergei.komarov.controllers;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.Employee;
import org.sergei.komarov.models.Project;
import org.sergei.komarov.models.ProjectRole;
import org.sergei.komarov.models.ProjectType;
import org.sergei.komarov.services.EmployeesService;
import org.sergei.komarov.services.ProjectRolesService;
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
    private final EmployeesService employeesService;
    private final ProjectRolesService projectRolesService;

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

    @PostMapping("/team/add")
    public Map<String, Object> addProjectTeamMember(int employeeId, int projectId, int projectRoleId) {
        Map<String, Object> attrs = new HashMap<>();

        try {
            Employee employee = employeesService.getById(employeeId);
            Project project = projectsService.getById(projectId);
            ProjectRole projectRole = projectRolesService.getById(projectRoleId);

            projectsService.addProjectTeamMember(project, employee, projectRole);

            attrs.put("info", "Сотрудник добавлен в команду проекта.");
        } catch (Exception e) {
            e.printStackTrace();
            attrs.put("error", "Невозможно добавить сотрудника в команду проекта.");
        }

        return attrs;
    }

    @PostMapping("/team/edit")
    public Map<String, Object> editProjectTeamMember(int employeeId, int projectId, int projectRoleId) {
        Map<String, Object> attrs = new HashMap<>();

        try {
            Employee employee = employeesService.getById(employeeId);
            Project project = projectsService.getById(projectId);
            ProjectRole projectRole = projectRolesService.getById(projectRoleId);

            projectsService.editProjectTeamMember(project, employee, projectRole);

            attrs.put("info", "Параметры участника проектной команды изменены.");
        } catch (Exception e) {
            e.printStackTrace();
            attrs.put("error", "Невозможно изменить параметры участника проектной команды");
        }

        return attrs;
    }

    @PostMapping("/team/delete")
    public Map<String, Object> deleteProjectTeamMember(int projectId, int employeeId) {
        Map<String, Object> attrs = new HashMap<>();

        try {
            Employee employee = employeesService.getById(employeeId);
            Project project = projectsService.getById(projectId);

            projectsService.deleteProjectTeamMember(project, employee);

            attrs.put("info", "Участник проектной команды удален.");
        } catch (Exception e) {
            e.printStackTrace();
            attrs.put("error", "Невозможно удалить участника проектной команды");
        }

        return attrs;
    }
}
