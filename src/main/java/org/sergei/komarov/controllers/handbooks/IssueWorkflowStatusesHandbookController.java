package org.sergei.komarov.controllers.handbooks;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/handbook")
public class IssueWorkflowStatusesHandbookController {

    @RequestMapping("/issueWorkflowStatuses")
    public String getIssueWorkflowStatusesViewPage() {

        return "issueWorkflowStatuses";
    }

    @RequestMapping("/issueWorkflowStatuses/add")
    public String getIssueWorkflowStatusesAddPage() {

        return "issueWorkflowStatuses";
    }

    @RequestMapping("/issueWorkflowStatuses/edit")
    public String getIssueWorkflowStatusesEditPage() {

        return "issueWorkflowStatuses";
    }

    @RequestMapping("/issueWorkflowStatuses/delete")
    public String getIssueWorkflowStatusesDeletePage() {

        return "issueWorkflowStatuses";
    }
}
