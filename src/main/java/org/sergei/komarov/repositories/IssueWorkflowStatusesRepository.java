package org.sergei.komarov.repositories;

import org.sergei.komarov.models.IssueWorkflowStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueWorkflowStatusesRepository extends JpaRepository<IssueWorkflowStatus, Integer> {
    List<IssueWorkflowStatus> findByIdIn(Iterable<Integer> ids);
}
