package org.sergei.komarov.repositories;

import org.sergei.komarov.models.WorkflowStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueWorkflowStatusesRepository extends JpaRepository<WorkflowStatus, Integer> {
}
