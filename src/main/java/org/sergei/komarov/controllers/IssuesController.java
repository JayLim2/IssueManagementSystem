package org.sergei.komarov.controllers;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.*;
import org.sergei.komarov.services.*;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/issues")
@AllArgsConstructor
public class IssuesController {

    private final IssuesService issuesService;
    private final ProjectsService projectsService;
    private final EmployeesService employeesService;
    private final IssueTypesService issueTypesService;
    private final IssuePrioritiesService issuePrioritiesService;
    private final IssueWorkflowStatusesService issueWorkflowStatusesService;

    @PostMapping("/add")
    public Map<String, Object> addIssue(String title,
                                        String dueDateStr,
                                        Integer assigneeId,
                                        Integer parentIssueId,
                                        @RequestParam int projectId,
                                        @RequestParam int typeId,
                                        @RequestParam int priorityId,
                                        @RequestParam int statusId,
                                        IssueDescriptionWrapper descriptionWrapper) {

        Map<String, Object> attrs = new HashMap<>();

        Employee assignee = assigneeId != null && assigneeId != 0 ? employeesService.getById(assigneeId) : null;
        Project project = projectsService.getById(projectId);
        Issue rootTask = parentIssueId != null && parentIssueId != 0 ? issuesService.getById(parentIssueId) : null;
        IssueType type = issueTypesService.getById(typeId);
        IssuePriority priority = issuePrioritiesService.getById(priorityId);
        IssueWorkflowStatus status = issueWorkflowStatusesService.getById(statusId);
        LocalDate dueDate = null;
        try {
            dueDate = LocalDate.parse(dueDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException | NullPointerException e) {
            e.printStackTrace();
        }

        String description = descriptionWrapper.getDescription();

        issuesService.validateAndSave(attrs, title, description, dueDate, assignee, project, rootTask, type, priority, status);

        return attrs;
    }

    // TODO: 23.05.2019 de-duplicate!
    @PostMapping("/edit")
    public Map<String, Object> editIssue(int issueId,
                                         String title,
                                         String dueDateStr,
                                         Integer assigneeId,
                                         Integer parentIssueId,
                                         int projectId, int typeId, int priorityId, int statusId,
                                         IssueDescriptionWrapper descriptionWrapper) {

        Map<String, Object> attrs = new HashMap<>();

        Employee assignee = assigneeId != null && assigneeId != 0 ? employeesService.getById(assigneeId) : null;
        Project project = projectsService.getById(projectId);
        Issue rootTask = parentIssueId != null && parentIssueId != 0 ? issuesService.getById(parentIssueId) : null;
        IssueType type = issueTypesService.getById(typeId);
        IssuePriority priority = issuePrioritiesService.getById(priorityId);
        IssueWorkflowStatus status = issueWorkflowStatusesService.getById(statusId);
        LocalDate dueDate = null;
        try {
            dueDate = LocalDate.parse(dueDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException | NullPointerException e) {
            e.printStackTrace();
        }

        // TODO: 06.06.2019 refactor!
        if (dueDate == null) {
            dueDate = issuesService.getById(issueId).getDueDate();
        }

        String description = descriptionWrapper.getDescription();

        issuesService.validateAndUpdate(attrs, issueId, title, description, dueDate, assignee, project, rootTask, type, priority, status);

        return attrs;
    }

    @PostMapping("/delete")
    public Map<String, Object> deleteIssue(int id) {

        Map<String, Object> attrs = new HashMap<>();

        boolean isExists = issuesService.isExistsById(id);
        attrs.put("isExists", isExists);
        String message = null;
        if (!isExists) {
            message = "Задача с таким ID не существует.";
        } else {
            try {
                issuesService.deleteById(id);
            } catch (TransactionSystemException e) {
                Throwable ex = SQLExceptionParser.getUnwrappedPSQLException(e);
                if (ex != null) {
                    ex.printStackTrace();
                    message = "Невозможно удалить задачу, т.к. есть зависимые сущности.";
                }
            }
        }

        if (message == null) {
            attrs.put("info", "Задача с ID " + id + " удалена.");
        } else {
            attrs.put("error", message);
        }

        return attrs;
    }
}