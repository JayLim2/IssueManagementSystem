package org.sergei.komarov.controllers.handbooks;

import lombok.AllArgsConstructor;
import org.sergei.komarov.services.IssueWorkflowStatusesService;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/handbook/issueWorkflowStatuses")
@AllArgsConstructor
public class IssueWorkflowStatusesHandbookController implements HandbookController {

    private final IssueWorkflowStatusesService issueWorkflowStatusesService;

    @PostMapping("/add")
    public Map<String, Object> handleAddRequest(String name) {
        Map<String, Object> attrs = new HashMap<>();

        issueWorkflowStatusesService.validateAndSave(attrs, name);

        return attrs;
    }

    @PostMapping("/edit")
    public Map<String, Object> handleEditRequest(int id, String name) {
        Map<String, Object> attrs = new HashMap<>();

        issueWorkflowStatusesService.validateAndUpdate(attrs, id, name);

        return attrs;
    }

    @PostMapping("/delete")
    public Map<String, Object> handleDeleteRequest(int id) {
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
                    message = "Невозможно удалить статус, т.к. существуют зависимые задачи.";
                }
            }
        }

        if (message == null) {
            attrs.put("info", "Статус рабочего процесса с ID " + id + " удален.");
        } else {
            attrs.put("error", message);
        }

        return attrs;
    }
}
