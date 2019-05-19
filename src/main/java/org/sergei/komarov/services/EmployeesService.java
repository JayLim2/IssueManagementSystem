package org.sergei.komarov.services;

import org.sergei.komarov.models.Employee;
import org.sergei.komarov.repositories.EmployeesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeesService implements JpaService<Employee, Integer> {

    private final EmployeesRepository employeesRepository;

    public EmployeesService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    @Override
    public List<Employee> getAll() {
        return employeesRepository.findAll();
    }

    @Override
    public Employee getById(Integer id) {
        return employeesRepository.getOne(id);
    }

    @Override
    public boolean isExistsById(Integer id) {
        return employeesRepository.existsById(id);
    }

    @Override
    public void save(Employee entity) {
        employeesRepository.save(entity);
    }

    @Override
    public void saveAll(Iterable<Employee> iterable) {
        employeesRepository.saveAll(iterable);
    }

    @Override
    public void delete(Employee entity) {
        employeesRepository.delete(entity);
    }

    @Override
    public void deleteById(Integer id) {
        employeesRepository.deleteById(id);
    }
}
