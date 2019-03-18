package org.sergei.komarov.controllers.api;

import com.google.gson.JsonPrimitive;
import org.sergei.komarov.models.Employee;
import org.sergei.komarov.models.IssueType;
import org.sergei.komarov.models.User;
import org.sergei.komarov.models.UserRole;
import org.sergei.komarov.services.IssueTypesService;
import org.sergei.komarov.services.UserRolesService;
import org.sergei.komarov.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class APIController {

    private final UserRolesService userRolesService;
    private final UsersService usersService;

    @Autowired
    public APIController(UserRolesService userRolesService, UsersService usersService) {
        this.userRolesService = userRolesService;
        this.usersService = usersService;
    }

    @GetMapping("/createDefaultUsers")
    @ResponseBody
    public Map<String, Object> createDefaultUSers() {

        UserRole userRole = new UserRole();
        userRole.setName("ADMIN");
        userRolesService.save(userRole);
        User user = new User();
        user.setLogin("admin");
        user.setPassword(new BCryptPasswordEncoder().encode("admin"));
        user.setRole(userRole);
        user.setEmployee(new Employee());
        usersService.save(user);

        return new HashMap<>();
    }

}
