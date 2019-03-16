package org.sergei.komarov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IssuesController {



    @RequestMapping("/hello")
    //@ResponseBody
    public String hello() {
        return "hello";
    }

    @RequestMapping("/login")
    //@ResponseBody
    public String login() {
        return "login";
    }
}
