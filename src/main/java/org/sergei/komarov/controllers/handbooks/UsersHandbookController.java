package org.sergei.komarov.controllers.handbooks;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.UserRole;
import org.sergei.komarov.services.EmployeesService;
import org.sergei.komarov.services.UsersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/handbook/users")
@AllArgsConstructor
public class UsersHandbookController {

    private final EmployeesService employeesService;
    private final UsersService usersService;

    @PostMapping("/edit")
    public Map<String, Object> handleEditRequest(String id, String newPassword, UserRole userRole) {
        Map<String, Object> attrs = new HashMap<>();

        usersService.validateAndUpdate(attrs, id, newPassword, userRole);

        return attrs;
    }

}
