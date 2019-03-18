package org.sergei.komarov.controllers.handbooks;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/handbook")
public class IssueActionTypesHandbookController {

    @RequestMapping("/issueActionTypes")
    public String getIssueActionTypesHandbookViewPage() {

        return "issueActionTypes";
    }

    @RequestMapping("/issueActionTypes/add")
    public String getIssueActionTypesHandbookAddPage() {

        return "issueActionTypes";
    }

    @RequestMapping("/issueActionTypes/edit")
    public String getIssueActionTypesHandbookEditPage() {

        return "issueActionTypes";
    }

    @RequestMapping("/issueActionTypes/delete")
    public String getIssueActionTypesHandbookDeletePage() {

        return "issueActionTypes";
    }

}
