package org.sergei.komarov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardsController {

    @GetMapping(value = {"/", "/index", "/dashboards"})
    public String indexPage() {
        return "index";
    }
}
