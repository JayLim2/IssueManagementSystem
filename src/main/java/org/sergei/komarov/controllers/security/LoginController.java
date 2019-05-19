package org.sergei.komarov.controllers.security;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.User;
import org.sergei.komarov.services.UsersService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class LoginController {
    private final UsersService usersService;

    @RequestMapping("/login")
    public String loginPage(Model model,
                            @RequestParam(required = false) Object error) {
        if (error != null) {
            model.addAttribute("error", "Логин или пароль введены неверно.");
        }

        return "login";
    }

    @PostMapping("/loginSuccess")
    public RedirectView successLoginPage(HttpSession session) {
        session.setAttribute("user", usersService.getCurrentUser());

        return new RedirectView("/");
    }

}
