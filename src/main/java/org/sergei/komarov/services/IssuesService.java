package org.sergei.komarov.services;

import lombok.AllArgsConstructor;
import org.postgresql.util.PSQLException;
import org.sergei.komarov.models.*;
import org.sergei.komarov.repositories.IssuesRepository;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.sergei.komarov.utils.Validators;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class IssuesService implements JpaService<Issue, Integer> {

    private static final int EXPIRATION_PERIOD = 3;

    private final IssuesRepository issuesRepository;
    private final UsersService usersService;
    private final IssueActionsService issueActionsService;

    private final EntityManager entityManager;

    @Override
    public List<Issue> getAll() {
        return issuesRepository.findAll();
    }

    public List<Issue> getByProject(Project project) {
        return issuesRepository.findByProject(project);
    }

    @Override
    public Issue getById(Integer id) {
        return isExistsById(id) ? issuesRepository.getOne(id) : null;
    }

    @Override
    public boolean isExistsById(Integer id) {
        return issuesRepository.existsById(id);
    }

    @Override
    public void save(Issue entity) {
        issuesRepository.save(entity);
    }

    public Issue saveAndGet(Issue entity) {
        return issuesRepository.save(entity);
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
            Employee creator = usersService.getCurrentUser().getEmployee();
            issue.setCreator(creator);
            issue.setStatus(status);
            issue.setParent(rootTask);

            LocalDateTime now = LocalDateTime.now();
            issue.setCreatedDateTime(now);
            issue.setUpdatedDateTime(now);

            message = trySave(issue);

            issueActionsService.createAndSave(
                    issue, creator, ServiceComment.OPENED, null
            );
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
                Map<ServiceComment, Object> diffs = issueActionsService.getDifferences(
                        issue,
                        title,
                        description,
                        dueDate,
                        project,
                        null,
                        priority,
                        type,
                        status,
                        assignee,
                        rootTask
                );

                issue.setTitle(title);
                issue.setDescription(description);
                issue.setDueDate(dueDate);
                issue.setProject(project);
                issue.setPriority(priority);
                issue.setType(type);
                issue.setAssignee(assignee);
                Employee updater = usersService.getCurrentUser().getEmployee();
                issue.setStatus(status);
                issue.setParent(rootTask);

                LocalDateTime now = LocalDateTime.now();
                issue.setUpdatedDateTime(now);

                message = trySave(issue);

                //заполнение журнала
                // FIXME: 26.05.2019
                List<IssueAction> actions = new ArrayList<>();
                for (Map.Entry<ServiceComment, Object> diff : diffs.entrySet()) {
                    ServiceComment serviceComment = diff.getKey();
                    Object value = diff.getValue();
                    if (value instanceof LocalDate) {
                        value = DateTimeFormatter.ofPattern("dd.MM.yyyy").format((LocalDate) value);
                    }
                    actions.add(
                            issueActionsService.create(issue, updater, serviceComment, value.toString())
                    );
                }
                issueActionsService.saveAll(actions);
            }
        } else {
            message = "Задача с таким ID не существует.";
        }

        addMessageToAttributes(attrs, message, "Задача успешно изменена.");
    }

    @Override
    public String trySave(Issue entity) {
        String message = null;

        if (entity == null) {
            throw new NullPointerException();
        }

        Issue savedIssue;
        try {
            savedIssue = saveAndGet(entity);
            entity.setId(savedIssue.getId());
        } catch (TransactionSystemException e) {
            PSQLException ex = (PSQLException) SQLExceptionParser.getUnwrappedPSQLException(e);
            if (!Objects.equals(ex, null) && Objects.equals(ex.getSQLState(), UNIQUE_VIOLATION_CODE)) {
                message = "Объект с таким названием уже существует.";
            }
        }

        return message;
    }

    public List<Issue> getOverdueIssuesByProject(Project project) {
        List<Issue> issues = issuesRepository.findOverdueIssuesByProject(
                LocalDate.now(),
                project
        );
        return issues;
    }

    public List<Issue> getIssuesWithExpiringDueDateByProject(Project project) {
        return issuesRepository.findIssuesWithExpiringDueDateByProject(
                getExpirationPeriod(EXPIRATION_PERIOD),
                project
        );
    }

    public List<Issue> getIssuesWithoutDueDateByProject(Project project) {
        return issuesRepository.findIssuesWithoutDueDateByProject(project);
    }

    public int getOverdueIssuesByProjectCount(Project project) {
        return issuesRepository.countOverdueIssuesByProject(
                LocalDate.now(),
                project
        );
    }

    public int getIssuesWithoutDueDateByProjectCount(Project project) {
        return issuesRepository.countIssuesWithoutDueDateByProject(project);
    }

    public int getIssuesWithExpiringDueDateByProjectCount(Project project) {
        return issuesRepository.countIssuesWithExpiringDueDateByProject(
                getExpirationPeriod(EXPIRATION_PERIOD),
                project
        );
    }

    public int getOverdueIssuesByEmployeeCount(Employee employee) {
        return issuesRepository.countOverdueIssuesByEmployee(
                LocalDate.now(),
                employee
        );
    }

    public int getIssuesWithoutDueDateByEmployeeCount(Employee employee) {
        return issuesRepository.countIssuesWithoutDueDateByEmployee(employee);
    }

    public int getIssuesWithExpiringDueDateByEmployeeCount(Employee employee) {
        return issuesRepository.countIssuesWithExpiringDueDateByEmployee(
                getExpirationPeriod(EXPIRATION_PERIOD),
                employee
        );
    }

    private LocalDate getExpirationPeriod(int daysToExpirationCount) {
        return LocalDate.now().plusDays(Math.abs(daysToExpirationCount));
    }
}
