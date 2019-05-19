package org.sergei.komarov.controllers.handbooks;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.UserRole;
import org.sergei.komarov.services.EmployeesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/handbook/users")
@AllArgsConstructor
public class UsersHandbookController implements HandbookController {

    private final EmployeesService employeesService;

    @Override
    @RequestMapping("/view")
    public String getViewPage(Model model) {
        return "users";
    }

    @Override
    @GetMapping("/add")
    public String getAddPage(Model model) {
        fillAttributes(model);

        return "addUser";
    }

    @Override
    @PostMapping("/add")
    public String handleAddRequest(Model model, String name) {
        fillAttributes(model);

        return "addUser";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String getEditPage(Model model, @PathVariable int id) {
        fillAttributes(model);

        return "editUser";
    }

    @Override
    @PostMapping("/edit/{id}")
    public String handleEditRequest(Model model, @PathVariable int id, String name) {
        fillAttributes(model);

        return "editUser";
    }

    @Override
    @GetMapping("/delete/{id}")
    public String handleDeleteRequest(Model model, @PathVariable int id) {
        return "deleteUser";
    }

    //support methods
    private void fillAttributes(Model model) {
        model.addAttribute("roles", UserRole.values());
        model.addAttribute("employees", employeesService.getAll());
    }
}
