package org.sergei.komarov.services;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.Employee;
import org.sergei.komarov.models.TimeSheet;
import org.sergei.komarov.models.keys.TimeSheetKey;
import org.sergei.komarov.repositories.TimeSheetsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TimeSheetsService implements JpaService<TimeSheet, TimeSheetKey> {

    private final TimeSheetsRepository timeSheetsRepository;

    @Override
    public List<TimeSheet> getAll() {
        return timeSheetsRepository.findAll();
    }

    @Override
    public TimeSheet getById(TimeSheetKey id) {
        return timeSheetsRepository.getOne(id);
    }

    @Override
    public boolean isExistsById(TimeSheetKey id) {
        return timeSheetsRepository.existsById(id);
    }

    @Override
    public void save(TimeSheet entity) {
        timeSheetsRepository.save(entity);
    }

    @Override
    public void saveAll(Iterable<TimeSheet> iterable) {
        timeSheetsRepository.saveAll(iterable);
    }

    @Override
    public void delete(TimeSheet entity) {
        timeSheetsRepository.delete(entity);
    }

    @Override
    public void deleteById(TimeSheetKey id) {
        timeSheetsRepository.deleteById(id);
    }

    public List<TimeSheet> getByEmployee(Employee employee) {
        return timeSheetsRepository.findByEmployee(employee);
    }
}
