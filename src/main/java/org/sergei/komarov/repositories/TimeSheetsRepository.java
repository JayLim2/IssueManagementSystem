package org.sergei.komarov.repositories;

import org.sergei.komarov.models.TimeSheet;
import org.sergei.komarov.models.keys.TimeSheetKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSheetsRepository extends JpaRepository<TimeSheet, TimeSheetKey> {
}
