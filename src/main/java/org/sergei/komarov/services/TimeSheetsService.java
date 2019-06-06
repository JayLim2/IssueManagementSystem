package org.sergei.komarov.services;

import org.sergei.komarov.models.TimeSheet;
import org.sergei.komarov.models.keys.TimeSheetKey;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeSheetsService implements JpaService<TimeSheet, TimeSheetKey> {
    @Override
    public List<TimeSheet> getAll() {
        return null;
    }

    @Override
    public TimeSheet getById(TimeSheetKey id) {
        return null;
    }

    @Override
    public boolean isExistsById(TimeSheetKey id) {
        return false;
    }

    @Override
    public void save(TimeSheet entity) {

    }

    @Override
    public void saveAll(Iterable<TimeSheet> iterable) {

    }

    @Override
    public void delete(TimeSheet entity) {

    }

    @Override
    public void deleteById(TimeSheetKey id) {

    }
}
