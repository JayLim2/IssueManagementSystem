package org.sergei.komarov.repositories;

import org.sergei.komarov.models.IssueWorkflowStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueWorkflowStatusesRepository extends JpaRepository<IssueWorkflowStatus, Integer> {
}
