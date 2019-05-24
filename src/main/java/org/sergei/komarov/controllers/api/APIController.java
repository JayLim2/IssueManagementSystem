package org.sergei.komarov.controllers.api;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.Employee;
import org.sergei.komarov.models.User;
import org.sergei.komarov.models.UserRole;
import org.sergei.komarov.services.UsersService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
@AllArgsConstructor
public class APIController {

    private final UsersService usersService;

    @GetMapping("/createDefaultUsers")
    @ResponseBody
    public Map<String, Object> createDefaultUSers() {

        User user = new User();
        user.setLogin("admin");
        user.setPassword(new BCryptPasswordEncoder().encode("admin"));
        user.setRole(UserRole.ADMIN);
        user.setEmployee(new Employee());
        usersService.save(user);

        return new HashMap<>();
    }

}
