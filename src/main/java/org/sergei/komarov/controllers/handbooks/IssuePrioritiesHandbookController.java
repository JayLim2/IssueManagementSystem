package org.sergei.komarov.controllers.handbooks;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/handbook/issuePriorities")
public class IssuePrioritiesHandbookController {

    @RequestMapping("/issuePriorities")
    public String getIssuePrioritiesHandbookViewPage() {

        return "issuePriorities";
    }

    @RequestMapping("/issuePriorities/add")
    public String getIssuePrioritiesHandbookAddPage() {

        return "issuePriorities";
    }

    @RequestMapping("/issuePriorities/edit")
    public String getIssuePrioritiesHandbookEditPage() {

        return "issuePriorities";
    }

    @RequestMapping("/issuePriorities/delete")
    public String getIssuePrioritiesHandbookDeletePage() {

        return "issuePriorities";
    }
}
