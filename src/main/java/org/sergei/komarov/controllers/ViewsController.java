package org.sergei.komarov.controllers;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.*;
import org.sergei.komarov.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/handbook")
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

    @GetMapping("/projectTypes")
    public String getProjectTypesViewPage(Model model) {

        List<ProjectType> projectTypes = projectTypesService.getAll();
        model.addAttribute("entities", projectTypes);

        return "projectTypes";
    }

    @GetMapping("/projectRoles")
    public String getProjectRolesViewPage(Model model) {

        List<ProjectRole> projectRoles = projectRolesService.getAll();
        model.addAttribute("entities", projectRoles);

        return "projectRoles";
    }

    @GetMapping("/employeePositions")
    public String getEmployeePositionsViewPage(Model model) {

        List<EmployeePosition> employeePositions = employeePositionsService.getAll();
        model.addAttribute("entities", employeePositions);

        return "employeePositions";
    }

    @GetMapping("/employees")
    public String getEmployeesViewPage(Model model) {

        List<Employee> employees = employeesService.getAll();
        model.addAttribute("entities", employees);

        return "employees";
    }

    @GetMapping("/users")
    public String getUsersViewPage(Model model) {

        List<User> users = usersService.getAll();
        model.addAttribute("entities", users);

        return "users";
    }

    @GetMapping("/issueWorkflowStatuses")
    public String getIssueWorkflowStatusesViewPage(Model model) {

        List<IssueWorkflowStatus> statuses = issueWorkflowStatusesService.getAll();
        model.addAttribute("entities", statuses);

        return "issueWorkflowStatuses";
    }

    @GetMapping("/issueTypes")
    public String getIssueTypesViewPage(Model model) {

        List<IssueType> issueTypes = issueTypesService.getAll();
        List<IssueWorkflowStatus> statuses = issueWorkflowStatusesService.getAll();

        model.addAttribute("entities", issueTypes);
        model.addAttribute("statuses", statuses);

        return "issueTypes";
    }

    @GetMapping("/issuePriorities")
    public String getIssuePrioritiesViewPage(Model model) {

        List<IssuePriority> issuePriorities = issuePrioritiesService.getAll();
        model.addAttribute("entities", issuePriorities);

        return "issuePriorities";
    }
}
