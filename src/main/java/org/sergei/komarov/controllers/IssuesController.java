package org.sergei.komarov.controllers;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.*;
import org.sergei.komarov.services.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/issues")
@AllArgsConstructor
public class IssuesController {

    private final IssuesService issuesService;
    private final ProjectsService projectsService;
    private final EmployeesService employeesService;
    private final IssueTypesService issueTypesService;
    private final IssuePrioritiesService issuePrioritiesService;
    private final IssueWorkflowStatusesService issueWorkflowStatusesService;

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

        List<Issue> issues = issuesService.getAll();
        model.addAttribute("issues", issues);

        return "issueInfo";
    }

    @GetMapping("/add")
    public String addIssue(Model model) {

        fillAttributes(model);

        return "addIssue";
    }


    @PostMapping("/add")
    public String addIssue(Model model,
                           String title,
                           String dueDateStr,
                           Integer assigneeId,
                           Integer rootTaskId,
                           int projectId, int typeId, int priorityId, int statusId,
                           IssueDescriptionWrapper descriptionWrapper) {

        fillAttributes(model);

        Map<String, Object> attrs = new HashMap<>();

        Employee assignee = assigneeId != null ? employeesService.getById(assigneeId) : null;
        Project project = projectsService.getById(projectId);
        Issue rootTask = rootTaskId != null ? issuesService.getById(rootTaskId) : null;
        IssueType type = issueTypesService.getById(typeId);
        IssuePriority priority = issuePrioritiesService.getById(priorityId);
        IssueWorkflowStatus status = issueWorkflowStatusesService.getById(statusId);
        LocalDate dueDate = dueDateStr != null ? LocalDate.parse(dueDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy")) : null;

        String description = descriptionWrapper.getDescription();

        issuesService.validateAndSave(attrs, title, description, dueDate, assignee, project, rootTask, type, priority, status);
        model.addAllAttributes(attrs);

        return "addIssue";
    }

    @GetMapping("/edit/{issueId}")
    public String editIssue(Model model, @PathVariable int issueId) {

        return "editIssue";
    }

    @PostMapping("/edit/{issueId}")
    public String editIssue(Model model, @PathVariable int issueId,
                            String title, String description, String dueDateStr,
                            int assigneeId, int projectId, int rootTaskId, int statusId) {

        return "editIssue";
    }

    @PostMapping("/delete/{issueId}")
    public String deleteIssue(@PathVariable int issueId) {

        return "delete";
    }

    //support methods
    private void fillAttributes(Model model) {
        List<Project> projects = projectsService.getAll();
        List<Employee> employees = employeesService.getAll();
        List<IssueType> issueTypes = issueTypesService.getAll();
        List<IssuePriority> issuePriorities = issuePrioritiesService.getAll();
        List<IssueWorkflowStatus> issueWorkflowStatuses = issueWorkflowStatusesService.getAll();

        model.addAttribute("projects", projects);
        model.addAttribute("employees", employees);
        model.addAttribute("issueTypes", issueTypes);
        model.addAttribute("issuePriorities", issuePriorities);
        model.addAttribute("issueWorkflowStatuses", issueWorkflowStatuses);

        model.addAttribute("descriptionWrapper", new IssueDescriptionWrapper());
    }
}