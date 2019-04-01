package org.sergei.komarov.controllers.handbooks;

import org.sergei.komarov.models.IssueType;
import org.sergei.komarov.models.WorkflowStatus;
import org.sergei.komarov.services.IssueTypesService;
import org.sergei.komarov.services.WorkflowStatusesService;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/handbook/issueTypes")
public class IssueTypesHandbookController {

    private IssueTypesService issueTypesService;
    private WorkflowStatusesService workflowStatusesService;

    public IssueTypesHandbookController(IssueTypesService issueTypesService, WorkflowStatusesService workflowStatusesService) {
        this.issueTypesService = issueTypesService;
        this.workflowStatusesService = workflowStatusesService;
    }

    @RequestMapping("/view")
    public String getViewPage(Model model) {

        List<IssueType> issueTypes = issueTypesService.getAll();
        Map<String, List<IssueType>> params = new HashMap<>();
        params.put("entities", issueTypes);
        model.addAllAttributes(params);

        return "issueTypes";
    }

    //-------------------------------- ADD

    @GetMapping("/add")
    public String getAddPage(Model model) {
        model.addAttribute("statuses", workflowStatusesService.getAll());

        return "addIssueType";
    }

    @PostMapping("/add")
    public String handleAddRequest(Model model, @RequestParam String name,
                                   @RequestParam(required = false) List<Integer> statuses) {

        Map<String, Object> attrs = new HashMap<>();
        attrs.put("statuses", workflowStatusesService.getAll());
        issueTypesService.validateAndSave(attrs, name, workflowStatusesService.getByIds(statuses));
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
            attrs.put("statuses", workflowStatusesService.getAll());
            attrs.put("entity", issueType);
        }
        model.addAllAttributes(attrs);

        return "editIssueType";
    }

    @PostMapping("/edit/{issueTypeId}")
    public String handleEditRequest(Model model, @PathVariable int issueTypeId, @RequestParam String name, @RequestParam List<Integer> statuses) {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("statuses", workflowStatusesService.getAll());
        issueTypesService.validateAndUpdate(attrs, issueTypeId, name, workflowStatusesService.getByIds(statuses));
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