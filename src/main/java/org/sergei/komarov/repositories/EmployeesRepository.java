package org.sergei.komarov.repositories;

import org.sergei.komarov.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeesRepository extends JpaRepository<Employee, Integer> {
}
