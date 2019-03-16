package org.sergei.komarov.controllers;

import org.sergei.komarov.models.TestModel;
import org.sergei.komarov.services.TestModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IssuesController {

    private final TestModelsService testModelsService;

    @Autowired
    public IssuesController(TestModelsService testModelsService) {
        this.testModelsService = testModelsService;
    }

    @RequestMapping("/hello")
    //@ResponseBody
    public String hello() {
        TestModel model = new TestModel();
        model.setName("AAAAAA2334124321");
        System.out.println(testModelsService.save(model));

        return "hello";
    }

    @RequestMapping("/login")
    //@ResponseBody
    public String login() {
        return "login";
    }
}
