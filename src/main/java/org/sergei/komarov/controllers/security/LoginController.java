package org.sergei.komarov.controllers.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String loginPage(Model model, @RequestParam(required = false) Object error) {
        if (error != null) {
            model.addAttribute("error", "Логин или пароль введены неверно.");
        }

        return "login";
    }

}
