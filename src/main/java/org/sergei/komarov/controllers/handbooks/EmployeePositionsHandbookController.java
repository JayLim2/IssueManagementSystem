package org.sergei.komarov.controllers.handbooks;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.EmployeePosition;
import org.sergei.komarov.services.EmployeePositionsService;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/handbook/employeePositions")
@AllArgsConstructor
public class EmployeePositionsHandbookController implements HandbookController {

    private final EmployeePositionsService employeePositionsService;

    @Override
    @RequestMapping("/view")
    public String getViewPage(Model model) {

        List<EmployeePosition> employeePositions = employeePositionsService.getAll();
        model.addAttribute("entities", employeePositions);

        return "employeePositions";
    }

    @Override
    @GetMapping("/add")
    public String getAddPage(Model model) {
        return "addEmployeePosition";
    }

    @Override
    @PostMapping("/add")
    public String handleAddRequest(Model model, @RequestParam String name) {

        Map<String, Object> attrs = new HashMap<>();

        employeePositionsService.validateAndSave(attrs, name);
        model.addAllAttributes(attrs);

        return "addEmployeePosition";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String getEditPage(Model model, @PathVariable int id) {
        Map<String, Object> attrs = new HashMap<>();

        if (!employeePositionsService.isExistsById(id)) {
            attrs.put("error", "Должность с таким ID не существует.");
        } else {
            EmployeePosition employeePosition = employeePositionsService.getById(id);
            attrs.put("entity", employeePosition);
        }
        model.addAllAttributes(attrs);

        return "editEmployeePosition";
    }

    @Override
    @PostMapping("/edit/{id}")
    public String handleEditRequest(Model model, @PathVariable int id, @RequestParam String name) {
        Map<String, Object> attrs = new HashMap<>();

        employeePositionsService.validateAndUpdate(attrs, id, name);
        model.addAllAttributes(attrs);

        return "editEmployeePosition";
    }

    @Override
    @PostMapping("/delete/{id}")
    public String handleDeleteRequest(Model model, @PathVariable int id) {

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
                    message = ex.getMessage();
                }
            }
        }

        if (message == null) {
            attrs.put("info", "Должность сотрудников с ID " + id + " удалена.");
        } else {
            attrs.put("error", message);
        }

        model.addAllAttributes(attrs);

        return "delete";
    }
}
