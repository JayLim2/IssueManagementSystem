package org.sergei.komarov.controllers.handbooks;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.User;
import org.sergei.komarov.models.UserRole;
import org.sergei.komarov.services.EmployeesService;
import org.sergei.komarov.services.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/handbook/users")
@AllArgsConstructor
public class UsersHandbookController {

    private final EmployeesService employeesService;
    private final UsersService usersService;

    @RequestMapping("/view")
    public String getViewPage(Model model) {

        List<User> users = usersService.getAll();
        model.addAttribute("entities", users);

        return "users";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(Model model, @PathVariable String id) {
        fillAttributes(model);

        Map<String, Object> attrs = new HashMap<>();

        if (!usersService.isExistsById(id)) {
            attrs.put("error", "Пользователь с таким логином не существует.");
        } else {
            User user = usersService.getById(id);
            attrs.put("entity", user);
        }
        model.addAllAttributes(attrs);

        return "editUser";
    }

    @PostMapping("/edit/{id}")
    public String handleEditRequest(Model model, @PathVariable String id, String newPassword, UserRole userRole) {
        fillAttributes(model);

        Map<String, Object> attrs = new HashMap<>();

        usersService.validateAndUpdate(attrs, id, newPassword, userRole);
        model.addAllAttributes(attrs);

        return "editUser";
    }

    //support methods
    private void fillAttributes(Model model) {
        model.addAttribute("roles", UserRole.values());
        model.addAttribute("employees", employeesService.getAll());
    }
}
