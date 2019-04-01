package org.sergei.komarov.repositories;

import org.sergei.komarov.models.WorkflowStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkflowStatusesRepository extends JpaRepository<WorkflowStatus, Integer> {
    List<WorkflowStatus> findByIdIn(Iterable<Integer> ids);
}
