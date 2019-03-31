package org.sergei.komarov.services;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.WorkflowStatus;
import org.sergei.komarov.repositories.IssueWorkflowStatusesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IssueWorkflowStatusesService {
    private final IssueWorkflowStatusesRepository issueWorkflowStatusesRepository;

    public List<WorkflowStatus> getAll() {
        return issueWorkflowStatusesRepository.findAll();
    }

    public void save(WorkflowStatus workflowStatus) {
        issueWorkflowStatusesRepository.save(workflowStatus);
    }

    public void saveAll(Iterable<WorkflowStatus> issueWorkflowStatuses) {
        issueWorkflowStatusesRepository.saveAll(issueWorkflowStatuses);
    }
}
