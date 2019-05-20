package org.sergei.komarov.controllers.handbooks;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.IssueType;
import org.sergei.komarov.services.IssueTypesService;
import org.sergei.komarov.services.IssueWorkflowStatusesService;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/handbook/issueTypes")
@AllArgsConstructor
public class IssueTypesHandbookController {

    private IssueTypesService issueTypesService;
    private IssueWorkflowStatusesService issueWorkflowStatusesService;

    @RequestMapping("/view")
    public String getViewPage(Model model) {

        List<IssueType> issueTypes = issueTypesService.getAll();
        model.addAttribute("entities", issueTypes);

        return "issueTypes";
    }

    //-------------------------------- ADD

    @GetMapping("/add")
    public String getAddPage(Model model) {
        model.addAttribute("statuses", issueWorkflowStatusesService.getAll());

        return "addIssueType";
    }

    @PostMapping("/add")
    public String handleAddRequest(Model model, @RequestParam String name,
                                   @RequestParam(required = false) List<Integer> statuses) {

        Map<String, Object> attrs = new HashMap<>();
        attrs.put("statuses", issueWorkflowStatusesService.getAll());
        issueTypesService.validateAndSave(attrs, name, issueWorkflowStatusesService.getByIds(statuses));
        model.addAllAttributes(attrs);

        return "addIssueType";
    }

    //--------------------------------- EDIT

    @GetMapping("/edit/{issueTypeId}")
    public String getEditPage(Model model, @PathVariable int issueTypeId) {

        Map<String, Object> attrs = new HashMap<>();

        if (!issueTypesService.isExistsById(issueTypeId)) {
            attrs.put("error", "Приоритет с таким ID не существует.");
        } else {
            IssueType issueType = issueTypesService.getById(issueTypeId);
            attrs.put("statuses", issueWorkflowStatusesService.getAll());
            attrs.put("entity", issueType);
        }
        model.addAllAttributes(attrs);

        return "editIssueType";
    }

    @PostMapping("/edit/{issueTypeId}")
    public String handleEditRequest(Model model, @PathVariable int issueTypeId, @RequestParam String name, @RequestParam List<Integer> statuses) {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("statuses", issueWorkflowStatusesService.getAll());
        issueTypesService.validateAndUpdate(attrs, issueTypeId, name, issueWorkflowStatusesService.getByIds(statuses));
        model.addAllAttributes(attrs);

        return "editIssueType";
    }

    //-------------------------------- DELETE

    @GetMapping("/delete/{issueTypeId}")
    public String handleDeleteRequest(Model model, @PathVariable int issueTypeId) {

        Map<String, Object> attrs = new HashMap<>();

        boolean isExists = issueTypesService.isExistsById(issueTypeId);
        attrs.put("isExists", isExists);
        String message = null;
        if (!isExists) {
            message = "Тип задачи с таким ID не существует.";
        } else {
            try {
                issueTypesService.deleteById(issueTypeId);
            } catch (TransactionSystemException e) {
                Throwable ex = SQLExceptionParser.getUnwrappedPSQLException(e);
                if (ex != null) {
                    ex.printStackTrace();
                    message = ex.getMessage();
                }
            }
        }

        if (message == null) {
            attrs.put("info", "Тип задачи с ID " + issueTypeId + " удален.");
        } else {
            attrs.put("error", message);
        }

        model.addAllAttributes(attrs);

        return "deleteIssueType";
    }

}