package org.sergei.komarov.services;

import org.sergei.komarov.models.EmployeePosition;
import org.sergei.komarov.repositories.EmployeePositionsRepository;
import org.sergei.komarov.utils.Validators;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmployeePositionsService implements JpaService<EmployeePosition, Integer> {

    private final EmployeePositionsRepository employeePositionsRepository;

    public EmployeePositionsService(EmployeePositionsRepository employeePositionsRepository) {
        this.employeePositionsRepository = employeePositionsRepository;
    }

    @Override
    public List<EmployeePosition> getAll() {
        return employeePositionsRepository.findAll();
    }

    @Override
    public EmployeePosition getById(Integer id) {
        return employeePositionsRepository.getOne(id);
    }

    @Override
    public boolean isExistsById(Integer id) {
        return employeePositionsRepository.existsById(id);
    }

    @Override
    public void save(EmployeePosition entity) {
        employeePositionsRepository.save(entity);
    }

    public EmployeePosition saveAndGet(EmployeePosition entity) {
        return employeePositionsRepository.save(entity);
    }

    @Override
    public void saveAll(Iterable<EmployeePosition> iterable) {
        employeePositionsRepository.saveAll(iterable);
    }

    @Override
    public void delete(EmployeePosition entity) {
        employeePositionsRepository.delete(entity);
    }

    @Override
    public void deleteById(Integer id) {
        employeePositionsRepository.deleteById(id);
    }

    public void validateAndSave(Map<String, Object> attrs, String name) {
        if (name == null || attrs == null) {
            throw new NullPointerException();
        }

        String message = Validators.validateEmployeePositionData(name);
        if (message == null) {
            EmployeePosition position = new EmployeePosition();
            position.setName(name);
            message = trySave(position);
        }

        addMessageToAttributes(attrs, message, "Успешно создана.");
    }

    public void validateAndUpdate(Map<String, Object> attrs, int id, String name) {
        if (name == null || attrs == null) {
            throw new NullPointerException();
        }

        String message;
        if (isExistsById(id)) {
            message = Validators.validateEmployeePositionData(name);

            EmployeePosition position = getById(id);
            if (message == null) {
                position.setName(name);
                message = trySave(position);
            } else {
                position.setName(null);
            }
            attrs.put("entity", position);
        } else {
            message = "Должность с таким ID не существует.";
        }

        addMessageToAttributes(attrs, message, "Успешно изменена.");
    }
}
