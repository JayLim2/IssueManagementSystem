package org.sergei.komarov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/issues")
public class IssuesController {

    @RequestMapping("/")
    public String getIssues() {

        return "issues";
    }

    @RequestMapping("/view/{issueId}")
    public String getIssue(@PathVariable int issueId) {

        return "issue";
    }

    @RequestMapping("/add")
    public String addIssue() {

        return "issue";
    }

    @RequestMapping("/edit/{issueId}")
    public String editIssue(@PathVariable int issueId) {

        return "issue";
    }

    @RequestMapping("/delete/{issueId}")
    public String deleteIssue(@PathVariable int issueId) {

        return "issue";
    }
}