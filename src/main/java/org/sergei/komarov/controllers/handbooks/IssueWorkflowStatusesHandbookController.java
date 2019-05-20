package org.sergei.komarov.controllers.handbooks;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.IssueWorkflowStatus;
import org.sergei.komarov.services.IssueWorkflowStatusesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/handbook/issueWorkflowStatuses")
@AllArgsConstructor
public class IssueWorkflowStatusesHandbookController implements HandbookController {

    private final IssueWorkflowStatusesService issueWorkflowStatusesService;

    @Override
    @RequestMapping("/view")
    public String getViewPage(Model model) {

        List<IssueWorkflowStatus> statuses = issueWorkflowStatusesService.getAll();
        model.addAttribute("entities", statuses);

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
    @PostMapping("/delete/{id}")
    public String handleDeleteRequest(Model model, @PathVariable int id) {
        return "delete";
    }
}
