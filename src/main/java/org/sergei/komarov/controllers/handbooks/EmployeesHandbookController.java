package org.sergei.komarov.controllers.handbooks;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.EmployeePosition;
import org.sergei.komarov.services.EmployeePositionsService;
import org.sergei.komarov.services.EmployeesService;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/handbook/employees")
@AllArgsConstructor
public class EmployeesHandbookController {

    private final EmployeesService employeesService;
    private final EmployeePositionsService employeePositionsService;

    @PostMapping("/add")
    public Map<String, Object> handleAddRequest(String firstName, String middleName, String lastName,
                                                int employeePositionId) {

        Map<String, Object> attrs = new HashMap<>();
        fillAttributes(attrs);
        EmployeePosition employeePosition = employeePositionsService.getById(employeePositionId);
        employeesService.validateAndSave(attrs, firstName, middleName, lastName, employeePosition);

        return attrs;
    }

    @PostMapping("/edit")
    public Map<String, Object> handleEditRequest(int id, String name) {
        Map<String, Object> attrs = new HashMap<>();
        fillAttributes(attrs);

        return attrs;
    }

    @PostMapping("/delete")
    public Map<String, Object> handleDeleteRequest(int id) {

        Map<String, Object> attrs = new HashMap<>();

        boolean isExists = employeesService.isExistsById(id);
        attrs.put("isExists", isExists);
        String message = null;
        if (!isExists) {
            message = "Сотрудник с таким ID не существует.";
        } else {
            try {
                employeesService.deleteById(id);
            } catch (TransactionSystemException e) {
                Throwable e2 = SQLExceptionParser.getUnwrappedPSQLException(e);
                if (e2 != null) {
                    e2.printStackTrace();
                    message = e2.getMessage();
                }
            }
        }

        if (message == null) {
            attrs.put("info", "Сотрудник с ID " + id + " удален.");
        } else {
            attrs.put("error", message);
        }

        return attrs;
    }

    //support methods
    private void fillAttributes(Map<String, Object> attrs) {
        attrs.put("positions", employeePositionsService.getAll());
    }
}
