package org.sergei.komarov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
public class ProjectsController {

    @RequestMapping("/")
    public String getProjects() {
        return "projects";
    }

    @RequestMapping("/view/{projectId}")
    public String getProject(@PathVariable int projectId) {
        return "project";
    }

    @RequestMapping("/add")
    public String addProject() {
        return "project";
    }

    @RequestMapping("/edit/{projectId}")
    public String editProject(@PathVariable int projectId) {
        return "project";
    }


    @RequestMapping("/delete/{projectId}")
    public String deleteProject(@PathVariable int projectId) {
        return "project";
    }
}
