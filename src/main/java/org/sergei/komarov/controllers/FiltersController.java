package org.sergei.komarov.controllers;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.*;
import org.sergei.komarov.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")
@AllArgsConstructor
public class FiltersController {

    private final IssuesService issuesService;
    private final ProjectsService projectsService;
    private final EmployeesService employeesService;
    private final IssueTypesService issueTypesService;
    private final IssuePrioritiesService issuePrioritiesService;
    private final IssueWorkflowStatusesService issueWorkflowStatusesService;

    @GetMapping("/issues")
    public String getIssuesByCriteria(Model model,
                                      @RequestParam(required = false) String title,
                                      @RequestParam(required = false) String description,
                                      @RequestParam(required = false) String dueDateStr,
                                      @RequestParam(required = false) List<Integer> projectsIds,
                                      @RequestParam(required = false) List<Integer> typesIds,
                                      @RequestParam(required = false) List<Integer> statusesIds,
                                      @RequestParam(required = false) List<Integer> prioritiesIds,
                                      @RequestParam(required = false) List<Employee> employeesIds) {

        List<Project> projects = projectsService.getAll();
        List<Employee> employees = employeesService.getAll();
        List<IssueType> types = issueTypesService.getAll();
        List<IssuePriority> priorities = issuePrioritiesService.getAll();
        List<IssueWorkflowStatus> statuses = issueWorkflowStatusesService.getAll();

        model.addAttribute("projects", projects);
        model.addAttribute("employees", employees);
        model.addAttribute("types", types);
        model.addAttribute("priorities", priorities);
        model.addAttribute("statuses", statuses);

        return "search";
    }
}
