package org.sergei.komarov.repositories;

import org.sergei.komarov.models.Employee;
import org.sergei.komarov.models.TimeSheet;
import org.sergei.komarov.models.keys.TimeSheetKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeSheetsRepository extends JpaRepository<TimeSheet, TimeSheetKey> {
    List<TimeSheet> findByEmployee(Employee employee);
}
