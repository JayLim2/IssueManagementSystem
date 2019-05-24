package org.sergei.komarov.controllers.handbooks;

import lombok.AllArgsConstructor;
import org.sergei.komarov.services.IssuePrioritiesService;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/handbook/issuePriorities")
@AllArgsConstructor
public class IssuePrioritiesHandbookController implements HandbookController {

    private final IssuePrioritiesService issuePrioritiesService;

    @PostMapping("/add")
    public Map<String, Object> handleAddRequest(String name) {
        Map<String, Object> attrs = new HashMap<>();

        issuePrioritiesService.validateAndSave(attrs, name);

        return attrs;
    }

    @PostMapping("/edit")
    public Map<String, Object> handleEditRequest(int id, String name) {
        Map<String, Object> attrs = new HashMap<>();

        issuePrioritiesService.validateAndUpdate(attrs, id, name);

        return attrs;
    }

    @PostMapping("/delete")
    public Map<String, Object> handleDeleteRequest(int id) {

        Map<String, Object> attrs = new HashMap<>();

        boolean isExists = issuePrioritiesService.isExistsById(id);
        attrs.put("isExists", isExists);
        String message = null;
        if (!isExists) {
            message = "Приоритет задач с таким ID не существует.";
        } else {
            try {
                issuePrioritiesService.deleteById(id);
            } catch (TransactionSystemException e) {
                Throwable ex = SQLExceptionParser.getUnwrappedPSQLException(e);
                if (ex != null) {
                    ex.printStackTrace();
                    message = "Невозможно удалить приоритет задач, т.к. существуют задачи с данным приоритетом.";
                }
            }
        }

        if (message == null) {
            attrs.put("info", "Приоритет задач с ID " + id + " удален.");
        } else {
            attrs.put("error", message);
        }

        return attrs;
    }
}
