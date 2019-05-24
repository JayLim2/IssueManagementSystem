package org.sergei.komarov.controllers.handbooks;

import lombok.AllArgsConstructor;
import org.sergei.komarov.services.ProjectTypesService;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/handbook/projectTypes")
@AllArgsConstructor
public class ProjectTypesHandbookController {
    private final ProjectTypesService projectTypesService;

    @PostMapping("/add")
    public Map<String, Object> handleAddRequest(String name) {
        Map<String, Object> attrs = new HashMap<>();

        projectTypesService.validateAndSave(attrs, name);

        return attrs;
    }

    @PostMapping("/edit")
    public Map<String, Object> handleEditRequest(int id, String name) {
        Map<String, Object> attrs = new HashMap<>();

        projectTypesService.validateAndUpdate(attrs, id, name);

        return attrs;
    }

    @PostMapping("/delete")
    public Map<String, Object> handleDeleteRequest(int id) {
        Map<String, Object> attrs = new HashMap<>();

        boolean isExists = projectTypesService.isExistsById(id);
        attrs.put("isExists", isExists);
        String message = null;
        if (!isExists) {
            message = "Тип проектов с таким ID не существует.";
        } else {
            try {
                projectTypesService.deleteById(id);
            } catch (TransactionSystemException e) {
                Throwable ex = SQLExceptionParser.getUnwrappedPSQLException(e);
                if (ex != null) {
                    ex.printStackTrace();
                    message = "Невозможно удалить, т.к. существуют проекты данного типа.";
                }
            }
        }

        if (message == null) {
            attrs.put("info", "Тип проектов с ID " + id + " удален.");
        } else {
            attrs.put("error", message);
        }

        return attrs;
    }
}