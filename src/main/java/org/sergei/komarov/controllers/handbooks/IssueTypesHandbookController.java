package org.sergei.komarov.controllers.handbooks;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/handbook/")
public class IssueTypesHandbookController {

    @RequestMapping("/issueTypes")
    public String getIssueTypesHandbookViewPage() {

        return "issueTypes";
    }

    @RequestMapping("/issueTypes/add")
    public String getIssueTypesHandbookAddPage() {

        return "issueTypes";
    }

    @RequestMapping("/issueTypes/edit")
    public String getIssueTypesHandbookEditPage() {

        return "issueTypes";
    }

    @RequestMapping("/issueTypes/delete")
    public String getIssueTypesHandbookDeletePage() {

        return "issueTypes";
    }

}