package org.sergei.komarov.controllers;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.*;
import org.sergei.komarov.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class ViewsController {

    private final IssueTypesService issueTypesService;
    private final IssuePrioritiesService issuePrioritiesService;
    private final IssueWorkflowStatusesService issueWorkflowStatusesService;
    private final ProjectTypesService projectTypesService;
    private final ProjectRolesService projectRolesService;
    private final EmployeePositionsService employeePositionsService;
    private final EmployeesService employeesService;
    private final UsersService usersService;
    private final ProjectsService projectsService;
    private final IssuesService issuesService;

    @GetMapping("/handbook/projectTypes")
    public String getProjectTypesViewPage(Model model) {

        List<ProjectType> projectTypes = projectTypesService.getAll();
        model.addAttribute("entities", projectTypes);

        return "projectTypes";
    }

    @GetMapping("/handbook/projectRoles")
    public String getProjectRolesViewPage(Model model) {

        List<ProjectRole> projectRoles = projectRolesService.getAll();
        model.addAttribute("entities", projectRoles);

        return "projectRoles";
    }

    @GetMapping("/handbook/employeePositions")
    public String getEmployeePositionsViewPage(Model model) {

        List<EmployeePosition> employeePositions = employeePositionsService.getAll();
        model.addAttribute("entities", employeePositions);

        return "employeePositions";
    }

    @GetMapping("/handbook/employees")
    public String getEmployeesViewPage(Model model) {

        List<Employee> employees = employeesService.getAll();
        List<EmployeePosition> employeePositions = employeePositionsService.getAll();

        model.addAttribute("entities", employees);
        model.addAttribute("positions", employeePositions);

        return "employees";
    }

    @GetMapping("/handbook/users")
    public String getUsersViewPage(Model model) {

        List<User> users = usersService.getAll();
        model.addAttribute("entities", users);

        model.addAttribute("roles", UserRole.values());

        return "users";
    }

    @GetMapping("/handbook/issueWorkflowStatuses")
    public String getIssueWorkflowStatusesViewPage(Model model) {

        List<IssueWorkflowStatus> statuses = issueWorkflowStatusesService.getAll();
        model.addAttribute("entities", statuses);

        return "issueWorkflowStatuses";
    }

    @GetMapping("/handbook/issueTypes")
    public String getIssueTypesViewPage(Model model) {

        List<IssueType> issueTypes = issueTypesService.getAll();
        List<IssueWorkflowStatus> statuses = issueWorkflowStatusesService.getAll();

        model.addAttribute("entities", issueTypes);
        model.addAttribute("statuses", statuses);

        return "issueTypes";
    }

    @GetMapping("/handbook/issuePriorities")
    public String getIssuePrioritiesViewPage(Model model) {

        List<IssuePriority> issuePriorities = issuePrioritiesService.getAll();
        model.addAttribute("entities", issuePriorities);

        return "issuePriorities";
    }

    //Страницы просмотра оперативных данных (проекты и задачи)

    @GetMapping("/projects/all")
    public String getProjects(Model model) {

        List<Project> projects = projectsService.getAll();
        List<ProjectType> projectTypes = projectTypesService.getAll();

        model.addAttribute("projects", projects);
        model.addAttribute("projectTypes", projectTypes);

        return "projects";
    }

    @GetMapping("/projects/{projectId}")
    public String getProject(Model model, @PathVariable int projectId) {

        Project project = projectsService.getById(projectId);
        if (project == null) {
            return "redirect:/projects/all";
        }

        List<Issue> issues = issuesService.getByProject(project);
        List<Project> projects = projectsService.getAll();
        List<ProjectType> projectTypes = projectTypesService.getAll();

        model.addAttribute("projectTypes", projectTypes);
        model.addAttribute("projects", projects);
        model.addAttribute("project", project);
        model.addAttribute("issues", issues);

        return "projectInfo";
    }
}
