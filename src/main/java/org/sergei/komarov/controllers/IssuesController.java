package org.sergei.komarov.controllers;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.*;
import org.sergei.komarov.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/issues")
@AllArgsConstructor
public class IssuesController {

    private final IssuesService issuesService;
    private final ProjectsService projectsService;
    private final EmployeesService employeesService;
    private final IssueTypesService issueTypesService;
    private final IssuePrioritiesService issuePrioritiesService;

    @RequestMapping("/view")
    public String getIssues(Model model) {

        List<Issue> issues = issuesService.getAll();
        model.addAttribute("issues", issues);

        return "issues";
    }

    @RequestMapping("/view/{issueId}")
    public String getIssue(Model model, @PathVariable int issueId) {

        Issue issue = issuesService.getById(issueId);
        model.addAttribute("issue", issue);

        return "issue";
    }

    @GetMapping("/add")
    public String addIssue(Model model) {

        List<Project> projects = projectsService.getAll();
        List<Employee> employees = employeesService.getAll();
        List<IssueType> issueTypes = issueTypesService.getAll();
        List<IssuePriority> issuePriorities = issuePrioritiesService.getAll();

        model.addAttribute("projects", projects);
        model.addAttribute("employees", employees);
        model.addAttribute("issueTypes", issueTypes);
        model.addAttribute("issuePriorities", issuePriorities);

        return "addIssue";
    }

    @PostMapping("/add")
    public String addIssue(Model model,
                           String title, String description, LocalDate dueDate,
                           int assigneeId, int projectId, int rootTaskId) {

        return "addIssue";
    }

    @GetMapping("/edit/{issueId}")
    public String editIssue(Model model, @PathVariable int issueId) {

        return "editIssue";
    }

    @PostMapping("/edit/{issueId}")
    public String editIssue(Model model, @PathVariable int issueId,
                            String title, String description, LocalDate dueDate,
                            int assigneeId, int projectId, int rootTaskId) {

        return "editIssue";
    }

    @PostMapping("/delete/{issueId}")
    public String deleteIssue(@PathVariable int issueId) {

        return "delete";
    }
}