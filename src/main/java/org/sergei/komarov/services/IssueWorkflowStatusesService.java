package org.sergei.komarov.services;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.IssueWorkflowStatus;
import org.sergei.komarov.repositories.IssueWorkflowStatusesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IssueWorkflowStatusesService {
    private final IssueWorkflowStatusesRepository issueWorkflowStatusesRepository;

    public List<IssueWorkflowStatus> getAll() {
        return issueWorkflowStatusesRepository.findAll();
    }

    public void save(IssueWorkflowStatus issueWorkflowStatus) {
        issueWorkflowStatusesRepository.save(issueWorkflowStatus);
    }

    public void saveAll(Iterable<IssueWorkflowStatus> issueWorkflowStatuses) {
        issueWorkflowStatusesRepository.saveAll(issueWorkflowStatuses);
    }
}
