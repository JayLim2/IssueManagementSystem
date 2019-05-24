package org.sergei.komarov.controllers.handbooks;

import lombok.AllArgsConstructor;
import org.sergei.komarov.services.EmployeePositionsService;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/handbook/employeePositions")
@AllArgsConstructor
public class EmployeePositionsHandbookController implements HandbookController {

    private final EmployeePositionsService employeePositionsService;

    @PostMapping("/add")
    public Map<String, Object> handleAddRequest(String name) {

        Map<String, Object> attrs = new HashMap<>();

        employeePositionsService.validateAndSave(attrs, name);

        return attrs;
    }

    @PostMapping("/edit")
    public Map<String, Object> handleEditRequest(int id, String name) {
        Map<String, Object> attrs = new HashMap<>();

        employeePositionsService.validateAndUpdate(attrs, id, name);

        return attrs;
    }

    @PostMapping("/delete")
    public Map<String, Object> handleDeleteRequest(int id) {

        Map<String, Object> attrs = new HashMap<>();

        boolean isExists = employeePositionsService.isExistsById(id);
        attrs.put("isExists", isExists);
        String message = null;
        if (!isExists) {
            message = "Должность сотрудников с таким ID не существует.";
        } else {
            try {
                employeePositionsService.deleteById(id);
            } catch (TransactionSystemException e) {
                Throwable ex = SQLExceptionParser.getUnwrappedPSQLException(e);
                if (ex != null) {
                    ex.printStackTrace();
                    message = "Невозможно удалить должность, т.к. существуют сотрудники, занимающие ее.";
                }
            }
        }

        if (message == null) {
            attrs.put("info", "Должность сотрудников с ID " + id + " удалена.");
        } else {
            attrs.put("error", message);
        }

        return attrs;
    }
}
