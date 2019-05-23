package org.sergei.komarov.services;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.*;
import org.sergei.komarov.repositories.IssuesRepository;
import org.sergei.komarov.utils.Validators;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class IssuesService implements JpaService<Issue, Integer> {

    private final IssuesRepository issuesRepository;
    private final UsersService usersService;

    @Override
    public List<Issue> getAll() {
        return issuesRepository.findAll();
    }

    @Override
    public Issue getById(Integer id) {
        return issuesRepository.getOne(id);
    }

    @Override
    public boolean isExistsById(Integer id) {
        return issuesRepository.existsById(id);
    }

    @Override
    public void save(Issue entity) {
        issuesRepository.save(entity);
    }

    @Override
    public void saveAll(Iterable<Issue> iterable) {
        issuesRepository.saveAll(iterable);
    }

    @Override
    public void delete(Issue entity) {
        issuesRepository.delete(entity);
    }

    @Override
    public void deleteById(Integer id) {
        issuesRepository.deleteById(id);
    }

    public void validateAndSave(Map<String, Object> attrs,
                                String title, String description, LocalDate dueDate,
                                Employee assignee, Project project, Issue rootTask,
                                IssueType type, IssuePriority priority, IssueWorkflowStatus status) {

        if (title == null || description == null || project == null || attrs == null) {
            throw new NullPointerException();
        }

        String message = Validators.validateIssueData(title, description, dueDate);
        if (message == null) {
            Issue issue = new Issue();
            issue.setTitle(title);
            issue.setDescription(description);
            issue.setDueDate(dueDate);
            issue.setProject(project);
            issue.setPriority(priority);
            issue.setType(type);
            issue.setAssignee(assignee);
            issue.setCreator(usersService.getCurrentUser().getEmployee());
            issue.setStatus(status);

            LocalDateTime now = LocalDateTime.now();
            issue.setCreatedDateTime(now);
            issue.setUpdatedDateTime(now);

            message = trySave(issue);
        }

        addMessageToAttributes(attrs, message, "Задача успешно создана.");
    }

    public void validateAndUpdate(Map<String, Object> attrs, int id,
                                  String title, String description, LocalDate dueDate,
                                  Employee assignee, Project project, Issue rootTask,
                                  IssueType type, IssuePriority priority, IssueWorkflowStatus status) {

        if (title == null || description == null || project == null || attrs == null) {
            throw new NullPointerException();
        }

        String message;
        if (isExistsById(id)) {
            message = Validators.validateIssueData(title, description, dueDate);

            Issue issue = getById(id);
            if (message == null) {
                issue.setTitle(title);
                issue.setDescription(description);
                issue.setDueDate(dueDate);
                issue.setProject(project);
                issue.setPriority(priority);
                issue.setType(type);
                issue.setAssignee(assignee);
                issue.setCreator(usersService.getCurrentUser().getEmployee());
                issue.setStatus(status);

                LocalDateTime now = LocalDateTime.now();
                issue.setCreatedDateTime(now);
                issue.setUpdatedDateTime(now);

                message = trySave(issue);
            }
            attrs.put("entity", issue);
        } else {
            message = "Задача с таким ID не существует.";
        }

        addMessageToAttributes(attrs, message, "Задача успешно изменена.");
    }
}
