package org.sergei.komarov.controllers.handbooks;

import org.sergei.komarov.models.IssuePriority;
import org.sergei.komarov.services.IssuePrioritiesService;
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
@RequestMapping("/handbook/issuePriorities")
public class IssuePrioritiesHandbookController implements HandbookController {

    private final IssuePrioritiesService issuePrioritiesService;

    @Autowired
    public IssuePrioritiesHandbookController(IssuePrioritiesService issuePrioritiesService) {
        this.issuePrioritiesService = issuePrioritiesService;
    }

    @RequestMapping("/view")
    public String getViewPage(Model model) {

        List<IssuePriority> issuePriorities = issuePrioritiesService.getAll();
        model.addAttribute("entities", issuePriorities);

        return "issuePriorities";
    }

    @GetMapping("/add")
    public String getAddPage(Model model) {

        return "addIssuePriority";
    }

    @PostMapping("/add")
    public String handleAddRequest(Model model, @RequestParam String name) {
        Map<String, Object> attrs = new HashMap<>();

        issuePrioritiesService.validateAndSave(attrs, name);
        model.addAllAttributes(attrs);

        return "addIssuePriority";
    }

    @GetMapping("/edit/{issuePriorityId}")
    public String getEditPage(Model model, @PathVariable int issuePriorityId) {
        Map<String, Object> attrs = new HashMap<>();

        if (!issuePrioritiesService.isExistsById(issuePriorityId)) {
            attrs.put("error", "Приоритет с таким ID не существует.");
        } else {
            IssuePriority issuePriority = issuePrioritiesService.getById(issuePriorityId);
            attrs.put("entity", issuePriority);
        }
        model.addAllAttributes(attrs);

        return "editIssuePriority";
    }

    @PostMapping("/edit/{issuePriorityId}")
    public String handleEditRequest(Model model, @PathVariable int issuePriorityId, @RequestParam String name) {
        Map<String, Object> attrs = new HashMap<>();

        issuePrioritiesService.validateAndUpdate(attrs, issuePriorityId, name);
        model.addAllAttributes(attrs);

        return "editIssuePriority";
    }

    @PostMapping("/delete/{issuePriorityId}")
    public String handleDeleteRequest(Model model, @PathVariable int issuePriorityId) {

        Map<String, Object> attrs = new HashMap<>();

        boolean isExists = issuePrioritiesService.isExistsById(issuePriorityId);
        attrs.put("isExists", isExists);
        String message = null;
        if (!isExists) {
            message = "Приоритет задач с таким ID не существует.";
        } else {
            try {
                issuePrioritiesService.deleteById(issuePriorityId);
            } catch (TransactionSystemException e) {
                Throwable e2 = SQLExceptionParser.getUnwrappedPSQLException(e);
                if (e2 != null) {
                    e2.printStackTrace();
                    message = e2.getMessage();
                }
            }
        }

        if (message == null) {
            attrs.put("info", "Приоритет задач с ID " + issuePriorityId + " удален.");
        } else {
            attrs.put("error", message);
        }

        model.addAllAttributes(attrs);

        return "delete";
    }
}
