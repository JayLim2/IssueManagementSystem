package org.sergei.komarov.controllers.handbooks;

import lombok.AllArgsConstructor;
import org.sergei.komarov.controllers.handbooks.HandbookController;
import org.sergei.komarov.models.Employee;
import org.sergei.komarov.models.EmployeePosition;
import org.sergei.komarov.models.UserRole;
import org.sergei.komarov.services.EmployeePositionsService;
import org.sergei.komarov.services.EmployeesService;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/handbook/employees")
@AllArgsConstructor
public class EmployeesHandbookController {

    private final EmployeesService employeesService;
    private final EmployeePositionsService employeePositionsService;

    @RequestMapping("/view")
    public String getViewPage(Model model) {

        List<Employee> employees = employeesService.getAll();
        model.addAttribute("entities", employees);

        return "employees";
    }

    @GetMapping("/add")
    public String getAddPage(Model model) {
        fillAttributes(model);

        return "addEmployee";
    }

    @PostMapping("/add")
    public String handleAddRequest(Model model,
                                   String firstName, String middleName, String lastName,
                                   int employeePositionId) {
        fillAttributes(model);

        Map<String, Object> attrs = new HashMap<>();
        EmployeePosition employeePosition = employeePositionsService.getById(employeePositionId);
        employeesService.validateAndSave(attrs, firstName, middleName, lastName, employeePosition);
        model.addAllAttributes(attrs);

        return "addEmployee";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(Model model, @PathVariable int id) {
        fillAttributes(model);

        return "editEmployee";
    }

    @PostMapping("/edit/{id}")
    public String handleEditRequest(Model model, @PathVariable int id, String name) {
        fillAttributes(model);

        return "editEmployee";
    }

    @RequestMapping("/delete/{id}")
    public String handleDeleteRequest(Model model, @PathVariable int id) {

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

        model.addAllAttributes(attrs);

        return "delete";
    }

    //support methods
    private void fillAttributes(Model model) {
        model.addAttribute("positions", employeePositionsService.getAll());
    }
}
