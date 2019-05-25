package org.sergei.komarov.controllers.handbooks;

import lombok.AllArgsConstructor;
import org.sergei.komarov.services.IssueTypesService;
import org.sergei.komarov.services.IssueWorkflowStatusesService;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/handbook/issueTypes")
@AllArgsConstructor
public class IssueTypesHandbookController {

    private IssueTypesService issueTypesService;
    private IssueWorkflowStatusesService issueWorkflowStatusesService;

    @PostMapping("/add")
    public Map<String, Object> handleAddRequest(String name, @RequestParam(required = false) List<Integer> statuses) {

        Map<String, Object> attrs = new HashMap<>();
        issueTypesService.validateAndSave(attrs, name, issueWorkflowStatusesService.getByIds(statuses));

        return attrs;
    }

    @PostMapping("/edit")
    public Map<String, Object> handleEditRequest(int id,
                                                 String name, @RequestParam(required = false) List<Integer> statuses) {
        Map<String, Object> attrs = new HashMap<>();
        issueTypesService.validateAndUpdate(attrs, id, name, issueWorkflowStatusesService.getByIds(statuses));

        return attrs;
    }

    @PostMapping("/delete")
    public Map<String, Object> handleDeleteRequest(int id) {

        Map<String, Object> attrs = new HashMap<>();

        boolean isExists = issueTypesService.isExistsById(id);
        attrs.put("isExists", isExists);
        String message = null;
        if (!isExists) {
            message = "Тип задач с таким ID не существует.";
        } else {
            try {
                issueTypesService.deleteById(id);
            } catch (TransactionSystemException e) {
                Throwable ex = SQLExceptionParser.getUnwrappedPSQLException(e);
                if (ex != null) {
                    ex.printStackTrace();
                    message = "Невозможно удалить тип задач, т.к. существуют задачи данного типа.";
                }
            }
        }

        if (message == null) {
            attrs.put("info", "Тип задач с ID " + id + " удален.");
        } else {
            attrs.put("error", message);
        }

        return attrs;
    }

}