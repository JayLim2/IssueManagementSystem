package org.sergei.komarov.controllers;

import org.sergei.komarov.models.IssueType;
import org.sergei.komarov.services.IssueTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class APIController {

    private final IssueTypesService issueTypesService;

    @Autowired
    public APIController(IssueTypesService issueTypesService) {
        this.issueTypesService = issueTypesService;
    }

    @GetMapping("/test")
    @ResponseBody
    public void test() {
        IssueType issueType = new IssueType();
        issueType.setTitle("TEST ISSUE TYPE");
        issueTypesService.save(issueType);
        issueTypesService.getAll().forEach(System.out::println);
        //return new JsonPrimitive("TESTED");
    }

}
