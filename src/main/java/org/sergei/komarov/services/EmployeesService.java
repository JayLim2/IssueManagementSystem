package org.sergei.komarov.services;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.Employee;
import org.sergei.komarov.models.EmployeePosition;
import org.sergei.komarov.models.User;
import org.sergei.komarov.repositories.EmployeesRepository;
import org.sergei.komarov.utils.Validators;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmployeesService implements JpaService<Employee, Integer> {

    private final EmployeesRepository employeesRepository;
    private final UsersService usersService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

    public void validateAndSave(Map<String, Object> attrs,
                                String firstName, String middleName, String lastName,
                                EmployeePosition employeePosition) {
        if (firstName == null || lastName == null || employeePosition == null || attrs == null) {
            throw new NullPointerException();
        }

        String message = Validators.validateEmployeeData(firstName, middleName, lastName, employeePosition);
        if (message == null) {
            Employee employee = new Employee();
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setMiddleName(middleName);
            employee.setPosition(employeePosition);

            User associatedUser = usersService.createUserByEmployee(employee);
            attrs.put("login", associatedUser.getLogin());
            attrs.put("password", associatedUser.getPassword());
            associatedUser.setPassword(bCryptPasswordEncoder.encode(associatedUser.getPassword()));
            associatedUser = usersService.saveAndGet(associatedUser);
            employee.setAssociatedUser(associatedUser);

            message = trySave(employee);
        }

        addMessageToAttributes(attrs, message, "Сотрудник успешно добавлен.");
    }

    public void validateAndUpdate(Map<String, Object> attrs, int id,
                                  String firstName, String middleName, String lastName,
                                  EmployeePosition employeePosition) {
        if (firstName == null || lastName == null || attrs == null) {
            throw new NullPointerException();
        }

        String message;
        if (isExistsById(id)) {
            message = Validators.validateEmployeeData(firstName, middleName, lastName, employeePosition);

            Employee employee = getById(id);
            if (message == null) {
                employee.setFirstName(firstName);
                employee.setMiddleName(middleName);
                employee.setLastName(middleName);
                employee.setPosition(employeePosition);
                message = trySave(employee);
            } else {
                employee = new Employee();
            }
            attrs.put("entity", employee);
        } else {
            message = "Сотрудник с таким ID не существует.";
        }

        addMessageToAttributes(attrs, message, "Данные о сотруднике успешно изменены.");
    }
}
