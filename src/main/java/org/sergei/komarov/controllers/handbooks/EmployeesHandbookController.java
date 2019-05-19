package org.sergei.komarov.controllers.handbooks;

import lombok.AllArgsConstructor;
import org.sergei.komarov.controllers.handbooks.HandbookController;
import org.sergei.komarov.models.UserRole;
import org.sergei.komarov.services.EmployeePositionsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/handbook/employees")
@AllArgsConstructor
public class EmployeesHandbookController implements HandbookController {

    private final EmployeePositionsService employeePositionsService;

    @Override
    @RequestMapping("/view")
    public String getViewPage(Model model) {
        return "employees";
    }

    @Override
    @GetMapping("/add")
    public String getAddPage(Model model) {
        fillAttributes(model);

        return "addEmployee";
    }

    @Override
    @PostMapping("/add")
    public String handleAddRequest(Model model, String name) {
        fillAttributes(model);

        return "addEmployee";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String getEditPage(Model model, @PathVariable int id) {
        fillAttributes(model);

        return "editEmployee";
    }

    @Override
    @PostMapping("/edit/{id}")
    public String handleEditRequest(Model model, @PathVariable int id, String name) {
        fillAttributes(model);

        return "editEmployee";
    }

    @Override
    @RequestMapping("/delete/{id}")
    public String handleDeleteRequest(Model model, @PathVariable int id) {
        return "deleteEmployee";
    }

    //support methods
    private void fillAttributes(Model model) {
        model.addAttribute("positions", employeePositionsService.getAll());
    }
}
