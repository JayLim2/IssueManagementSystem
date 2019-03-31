package org.sergei.komarov.controllers.handbooks;

import org.sergei.komarov.models.IssuePriority;
import org.sergei.komarov.services.IssuePrioritiesService;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.RollbackException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/handbook")
public class IssuePrioritiesHandbookController {

    private final IssuePrioritiesService issuePrioritiesService;

    @Autowired
    public IssuePrioritiesHandbookController(IssuePrioritiesService issuePrioritiesService) {
        this.issuePrioritiesService = issuePrioritiesService;
    }

    @RequestMapping("/issuePriorities")
    public String getIssuePrioritiesHandbookViewPage(Model model) {

        List<IssuePriority> issuePriorities = issuePrioritiesService.getAll();
        Map<String, List<IssuePriority>> params = new HashMap<>();
        params.put("entities", issuePriorities);
        model.addAllAttributes(params);

        return "issuePriorities";
    }

    @GetMapping("/issuePriorities/add")
    public String getIssuePrioritiesHandbookAddPage() {

        return "addIssuePriorities";
    }

    @PostMapping("/issuePriorities/add")
    public String getIssuePrioritiesHandbookAddPage(Model model, @RequestParam String name) {
        Map<String, Object> attrs = new HashMap<>();

        issuePrioritiesService.validateAndSave(attrs, name);
        model.addAllAttributes(attrs);

        return "addIssuePriorities";
    }

    @GetMapping("/issuePriorities/edit/{issuePriorityId}")
    public String getIssuePrioritiesHandbookEditPage(Model model, @PathVariable int issuePriorityId) {
        Map<String, Object> attrs = new HashMap<>();

        if (!issuePrioritiesService.isExistsById(issuePriorityId)) {
            attrs.put("error", "Приоритет с таким ID не существует.");
        } else {
            IssuePriority issuePriority = issuePrioritiesService.getById(issuePriorityId);
            attrs.put("entity", issuePriority);
        }
        model.addAllAttributes(attrs);

        return "editIssuePriorities";
    }

    @PostMapping("/issuePriorities/edit/{issuePriorityId}")
    public String getIssuePrioritiesHandbookEditPage(Model model, @PathVariable int issuePriorityId, @RequestParam String name) {
        Map<String, Object> attrs = new HashMap<>();

        issuePrioritiesService.validateAndUpdate(attrs, issuePriorityId, name);
        model.addAllAttributes(attrs);

        return "editIssuePriorities";
    }

    @GetMapping("/issuePriorities/delete/{issuePriorityId}")
    public String getIssuePrioritiesHandbookDeletePage(Model model, @PathVariable int issuePriorityId) {

        Map<String, Object> attrs = new HashMap<>();

        boolean isExists = issuePrioritiesService.isExistsById(issuePriorityId);
        attrs.put("isExists", isExists);
        if (!isExists) {
            attrs.put("error", "Приоритет с таким ID не существует.");
        } else {
            try {
                issuePrioritiesService.deleteById(issuePriorityId);
                attrs.put("info", "Приоритет с ID " + issuePriorityId + " удален.");
            } catch (TransactionSystemException e) {
                Throwable e2 = SQLExceptionParser.getUnwrappedPSQLException(e);
                String message = "???";
                if (e2 != null) {
                    e2.printStackTrace();
                    message = e2.getMessage();
                }
                attrs.put("error", message);
            }
        }

        model.addAllAttributes(attrs);

        return "deleteIssuePriorities";
    }
}
