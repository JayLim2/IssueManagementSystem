package org.sergei.komarov.controllers.handbooks;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/handbook/issueWorkflowStatuses")
public class IssueWorkflowStatusesHandbookController implements HandbookController {

    @Override
    @RequestMapping("/view")
    public String getViewPage(Model model) {
        return "issueWorkflowStatuses";
    }

    @Override
    @GetMapping("/add")
    public String getAddPage(Model model) {
        return "addIssueWorkflowStatus";
    }

    @Override
    @PostMapping("/add")
    public String handleAddRequest(Model model, @RequestParam String name) {
        return "addIssueWorkflowStatus";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String getEditPage(Model model, @PathVariable int id) {
        return "editIssueWorkflowStatus";
    }

    @Override
    @PostMapping("/edit/{id}")
    public String handleEditRequest(Model model, @PathVariable int id, @RequestParam String name) {
        return "editIssueWorkflowStatus";
    }

    @Override
    @GetMapping("/delete/{id}")
    public String handleDeleteRequest(Model model, @PathVariable int id) {
        return "deleteIssueWorkflowStatus";
    }
}
