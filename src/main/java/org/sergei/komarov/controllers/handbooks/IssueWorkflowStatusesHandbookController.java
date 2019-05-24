package org.sergei.komarov.controllers.handbooks;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.IssueWorkflowStatus;
import org.sergei.komarov.services.IssueWorkflowStatusesService;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/handbook/issueWorkflowStatuses")
@AllArgsConstructor
public class IssueWorkflowStatusesHandbookController implements HandbookController {

    private final IssueWorkflowStatusesService issueWorkflowStatusesService;

    @Override
    @GetMapping("/add")
    public String getAddPage(Model model) {
        return "addIssueWorkflowStatus";
    }

    @Override
    @PostMapping("/add")
    public String handleAddRequest(Model model, @RequestParam String name) {
        Map<String, Object> attrs = new HashMap<>();

        issueWorkflowStatusesService.validateAndSave(attrs, name);
        model.addAllAttributes(attrs);

        return "addIssueWorkflowStatus";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String getEditPage(Model model, @PathVariable int id) {

        Map<String, Object> attrs = new HashMap<>();

        if (!issueWorkflowStatusesService.isExistsById(id)) {
            attrs.put("error", "Статус рабочего процесса с таким ID не существует.");
        } else {
            IssueWorkflowStatus issueWorkflowStatus = issueWorkflowStatusesService.getById(id);
            attrs.put("entity", issueWorkflowStatus);
        }
        model.addAllAttributes(attrs);

        return "editIssueWorkflowStatus";
    }

    @Override
    @PostMapping("/edit/{id}")
    public String handleEditRequest(Model model, @PathVariable int id, @RequestParam String name) {
        Map<String, Object> attrs = new HashMap<>();

        issueWorkflowStatusesService.validateAndUpdate(attrs, id, name);
        model.addAllAttributes(attrs);

        return "editIssueWorkflowStatus";
    }

    @Override
    @PostMapping("/delete/{id}")
    public String handleDeleteRequest(Model model, @PathVariable int id) {
        Map<String, Object> attrs = new HashMap<>();

        boolean isExists = issueWorkflowStatusesService.isExistsById(id);
        attrs.put("isExists", isExists);
        String message = null;
        if (!isExists) {
            message = "Статус рабочего процесса с таким ID не существует.";
        } else {
            try {
                issueWorkflowStatusesService.deleteById(id);
            } catch (TransactionSystemException e) {
                Throwable e2 = SQLExceptionParser.getUnwrappedPSQLException(e);
                if (e2 != null) {
                    e2.printStackTrace();
                    message = e2.getMessage();
                }
            }
        }

        if (message == null) {
            attrs.put("info", "Статус рабочего процесса с ID " + id + " удален.");
        } else {
            attrs.put("error", message);
        }

        model.addAllAttributes(attrs);

        return "delete";
    }
}
