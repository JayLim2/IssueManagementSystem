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

        List<String> pr = Arrays.asList(
                "Minor", "Normal", "Major", "Critical"
        );

        Map<String, List<String>> params = new HashMap<>();
        params.put("pr", pr);
        model.addAllAttributes(params);

        return "issuePriorities";
    }

    @RequestMapping("/issuePriorities/add")
    public String getIssuePrioritiesHandbookAddPage() {

        return "addIssuePriorities";
    }

    @GetMapping("/issuePriorities/edit/{issuePriorityId}")
    public String getIssuePrioritiesHandbookEditPage(Model model,
                                                     @PathVariable int issuePriorityId) {
        Map<String, Object> attrs = new HashMap<>();

        boolean isExists = issuePrioritiesService.isExistsById(issuePriorityId);
        attrs.put("isExists", isExists);
        if (!isExists) {
            attrs.put("error", "Приоритет с таким ID не существует.");
        } else {
            IssuePriority issuePriority = issuePrioritiesService.getById(issuePriorityId);
            attrs.put("entity", issuePriority);
        }

        model.addAllAttributes(attrs);

        return "editIssuePriorities";
    }

    @PostMapping("/issuePriorities/edit/{issuePriorityId}")
    public String getIssuePrioritiesHandbookEditPage(Model model,
                                                     @PathVariable int issuePriorityId,
                                                     @RequestParam String name) {
        Map<String, Object> attrs = new HashMap<>();

        boolean isExists = issuePrioritiesService.isExistsById(issuePriorityId);
        attrs.put("isExists", isExists);
        if (!isExists) {
            attrs.put("error", "Приоритет с таким ID не существует.");
        } else if (name == null) {
            attrs.put("error", "NULL parameter");
        } else {
            IssuePriority issuePriority = issuePrioritiesService.getById(issuePriorityId);
            issuePriority.setPriorityTitle(name);
            try {
                issuePrioritiesService.save(issuePriority);
                attrs.put("info", "Изменения сохранены.");
            } catch (TransactionSystemException e) {
                Throwable e2 = SQLExceptionParser.getUnwrappedPSQLException(e);
                String message = "???";
                if (e2 != null) {
                    e2.printStackTrace();
                    message = e2.getMessage();
                }
                attrs.put("error", message);
            } finally {
                attrs.put("entity", issuePriority);
            }
        }

        model.addAllAttributes(attrs);

        return "editIssuePriorities";
    }

    @RequestMapping("/issuePriorities/delete")
    public String getIssuePrioritiesHandbookDeletePage() {

        return "issuePriorities";
    }
}
