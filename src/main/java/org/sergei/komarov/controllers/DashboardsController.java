package org.sergei.komarov.controllers;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.Employee;
import org.sergei.komarov.models.Issue;
import org.sergei.komarov.models.Project;
import org.sergei.komarov.services.EmployeesService;
import org.sergei.komarov.services.IssuesService;
import org.sergei.komarov.services.ProjectsService;
import org.sergei.komarov.utils.Comparators;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class DashboardsController {

    private final ProjectsService projectsService;
    private final EmployeesService employeesService;
    private final IssuesService issuesService;

    private final Comparators comparators;

    @GetMapping(value = {"/", "/index", "/dashboards"})
    public String indexPage() {
        return "redirect:/dashboards/projects";
    }

    @GetMapping("/dashboards/projects")
    public String getProjectsDashboard(Model model) {
        List<Project> projects = projectsService.getAll();
        projects.sort(comparators.getProjectsComparator());
        Map<Project, Map<String, List<Issue>>> dashboard = new LinkedHashMap<>();

        for (Project project : projects) {
            List<Issue> overdueIssuesByProject = issuesService.getOverdueIssuesByProject(project);
            List<Issue> withExpiringDueDateIssuesByProject = issuesService.getIssuesWithExpiringDueDateByProject(project);
            List<Issue> withoutDueDateIssuesByProject = issuesService.getIssuesWithoutDueDateByProject(project);

            if (overdueIssuesByProject.size() + withExpiringDueDateIssuesByProject.size() + withoutDueDateIssuesByProject.size() > 0) {
                Map<String, List<Issue>> issuesMap = new LinkedHashMap<>();
                issuesMap.put("Срок выполнения истек", overdueIssuesByProject);
                issuesMap.put("Срок выполнения скоро истечет", withExpiringDueDateIssuesByProject);
                issuesMap.put("Без срока выполнения", withoutDueDateIssuesByProject);

                dashboard.put(project, issuesMap);
            }
        }

        model.addAttribute("projectsDashboard", dashboard);

        return "projectsDashboard";
    }

    @GetMapping("/dashboards/employees")
    public String getEmployeesDashboard(Model model) {
        List<Employee> employees = employeesService.getAll();
        employees.sort(comparators.getEmployeesComparator());
        Map<Employee, Map<String, List<Issue>>> dashboard = new LinkedHashMap<>();

        for (Employee employee : employees) {
            List<Issue> overdueIssuesByEmployee = issuesService.getOverdueIssuesByEmployee(employee);
            List<Issue> withExpiringDueDateIssuesByEmployee = issuesService.getIssuesWithExpiringDueDateByEmployee(employee);
            List<Issue> withoutDueDateIssuesByEmployee = issuesService.getIssuesWithoutDueDateByEmployee(employee);

            if (overdueIssuesByEmployee.size() + withExpiringDueDateIssuesByEmployee.size() + withoutDueDateIssuesByEmployee.size() > 0) {
                Map<String, List<Issue>> issuesMap = new LinkedHashMap<>();
                issuesMap.put("Срок выполнения истек", overdueIssuesByEmployee);
                issuesMap.put("Срок выполнения скоро истечет", withExpiringDueDateIssuesByEmployee);
                issuesMap.put("Без срока выполнения", withoutDueDateIssuesByEmployee);

                dashboard.put(employee, issuesMap);
            }
        }

        model.addAttribute("employeesDashboard", dashboard);

        return "employeesDashboard";
    }

    @GetMapping("/dashboards/projectSchedule/{projectId}")
    public String getProjectSchedule(Model model, @PathVariable int projectId) {

        Project project = projectsService.getById(projectId);

        if (project == null) {
            model.addAttribute("error", "Проект с таким ID не существует.");
            return "projectSchedule";
        }

        List<Issue> issues = issuesService.getByProject(project);
        issues.sort(comparators.getIssuesComparator());

        model.addAttribute("project", project);
        model.addAttribute("issues", issues);

        return "projectSchedule";
    }
}
