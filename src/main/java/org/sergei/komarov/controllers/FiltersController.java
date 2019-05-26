package org.sergei.komarov.controllers;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.Employee;
import org.sergei.komarov.services.EmployeesService;
import org.sergei.komarov.services.IssuesService;
import org.sergei.komarov.services.ProjectsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")
@AllArgsConstructor
public class FiltersController {

    private final IssuesService issuesService;
    private final ProjectsService projectsService;
    private final EmployeesService employeesService;

    @GetMapping("/issues")
    public String getIssuesByCriteria(Model model,
                                      @RequestParam(required = false) String title,
                                      @RequestParam(required = false) String description,
                                      @RequestParam(required = false) String dueDateStr,
                                      @RequestParam(required = false) List<Integer> projectsIds,
                                      @RequestParam(required = false) List<Integer> typesIds,
                                      @RequestParam(required = false) List<Integer> statusesIds,
                                      @RequestParam(required = false) List<Integer> prioritiesIds,
                                      @RequestParam(required = false) List<Employee> employeesIds) {

        return "search";
    }
}
