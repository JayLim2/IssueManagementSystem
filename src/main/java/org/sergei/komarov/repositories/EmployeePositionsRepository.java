package org.sergei.komarov.repositories;

import org.sergei.komarov.models.EmployeePosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeePositionsRepository extends JpaRepository<EmployeePosition, Integer> {
}