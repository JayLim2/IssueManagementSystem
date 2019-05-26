package org.sergei.komarov.services;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.*;
import org.sergei.komarov.models.keys.IssueActionKey;
import org.sergei.komarov.repositories.IssueActionsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class IssueActionsService implements JpaService<IssueAction, IssueActionKey> {

    private final IssueActionsRepository issueActionsRepository;

    @Override
    public List<IssueAction> getAll() {
        return issueActionsRepository.findAll();
    }

    @Override
    public IssueAction getById(IssueActionKey id) {
        return isExistsById(id) ? issueActionsRepository.getOne(id) : null;
    }

    @Override
    public boolean isExistsById(IssueActionKey id) {
        return issueActionsRepository.existsById(id);
    }

    @Override
    public void save(IssueAction entity) {
        issueActionsRepository.save(entity);
    }

    public IssueAction saveAndGet(IssueAction entity) {
        return issueActionsRepository.save(entity);
    }

    @Override
    public void saveAll(Iterable<IssueAction> iterable) {
        issueActionsRepository.saveAll(iterable);
    }

    @Override
    public void delete(IssueAction entity) {
        issueActionsRepository.delete(entity);
    }

    @Override
    public void deleteById(IssueActionKey id) {
        issueActionsRepository.deleteById(id);
    }

    public IssueAction create(Issue issue, Employee employee,
                              ServiceComment serviceComment, String employeeComment) {

        if (issue == null || employee == null) {
            throw new NullPointerException("Issue and employee must be NOT NULL.");
        }

        IssueAction action = new IssueAction();
        LocalDateTime now = LocalDateTime.now();
        action.setDate(now);
        action.setIssue(issue);
        action.setEmployee(employee);
        action.setServiceComment(serviceComment);
        action.setEmployeeComment(employeeComment);

        return action;
    }

    public IssueAction createAndSave(Issue issue, Employee employee,
                                     ServiceComment serviceComment, String employeeComment) {

        IssueAction action = create(issue, employee, serviceComment, employeeComment);
        return saveAndGet(action);
    }

    public Map<ServiceComment, Object> getDifferences(Issue old,
                                                      String title, String description, LocalDate dueDate,
                                                      Project project, ProjectVersion projectVersion,
                                                      IssuePriority priority, IssueType type, IssueWorkflowStatus status,
                                                      Employee assignee, Issue parent) {

        Map<ServiceComment, Object> serviceComments = new HashMap<>();

        if (!Objects.equals(old.getTitle(), title)) {
            serviceComments.put(ServiceComment.CHANGED_TITLE, title);
        }

        if (!Objects.equals(old.getDescription(), description)) {
            serviceComments.put(ServiceComment.CHANGED_DESCRIPTION, description);
        }

        if (!Objects.equals(old.getDueDate(), dueDate)) {
            serviceComments.put(ServiceComment.CHANGED_DUE_DATE, dueDate);
        }

        if (!old.getProject().getId().equals(project.getId())) {
            serviceComments.put(ServiceComment.CHANGED_PROJECT, project);
        }

        boolean oldVersionIsNull = old.getVersion() == null;
        boolean newVersionIsNull = projectVersion == null;
        if ((oldVersionIsNull && !newVersionIsNull)
                || (!oldVersionIsNull && newVersionIsNull)
                || (!oldVersionIsNull && !old.getVersion().getId().equals(projectVersion.getId()))) {

            serviceComments.put(ServiceComment.CHANGED_PROJECT_VERSION, projectVersion);
        }

        if (!old.getPriority().getId().equals(priority.getId())) {
            serviceComments.put(ServiceComment.CHANGED_PRIORITY, priority);
        }

        if (!old.getType().getId().equals(type.getId())) {
            serviceComments.put(ServiceComment.CHANGED_TYPE, type);
        }

        if (!old.getStatus().getId().equals(status.getId())) {
            serviceComments.put(ServiceComment.CHANGED_WORKFLOW_STATUS, status);
        }

        boolean oldAssigneeIsNull = old.getAssignee() == null;
        boolean newAssigneeIsNull = assignee == null;
        if ((oldAssigneeIsNull && !newAssigneeIsNull)
                || (!oldAssigneeIsNull && newAssigneeIsNull)
                || (!oldAssigneeIsNull && !old.getAssignee().getId().equals(assignee.getId()))) {

            serviceComments.put(ServiceComment.CHANGED_ASSIGNEE, assignee);
        }

        boolean oldParentIssueIsNull = old.getParent() == null;
        boolean newParentIssueIsNull = parent == null;
        if ((oldParentIssueIsNull && !newParentIssueIsNull)
                || (!oldParentIssueIsNull && newParentIssueIsNull)
                || (!oldParentIssueIsNull && !old.getParent().getId().equals(parent.getId()))) {

            serviceComments.put(ServiceComment.CHANGED_PARENT_ISSUE, parent);
        }

        return serviceComments;

    }

    public List<IssueAction> getByIssue(Issue issue) {
        return issueActionsRepository.findByIssue(issue);
    }
}
