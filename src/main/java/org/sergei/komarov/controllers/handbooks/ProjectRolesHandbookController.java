package org.sergei.komarov.controllers.handbooks;

import lombok.AllArgsConstructor;
import org.sergei.komarov.services.ProjectRolesService;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/handbook/projectRoles")
@AllArgsConstructor
public class ProjectRolesHandbookController implements HandbookController {

    private final ProjectRolesService projectRolesService;

    @PostMapping("/add")
    public Map<String, Object> handleAddRequest(String name) {
        Map<String, Object> attrs = new HashMap<>();

        projectRolesService.validateAndSave(attrs, name);

        return attrs;
    }

    @PostMapping("/edit")
    public Map<String, Object> handleEditRequest(int id, String name) {
        Map<String, Object> attrs = new HashMap<>();

        projectRolesService.validateAndUpdate(attrs, id, name);

        return attrs;
    }

    @PostMapping("/delete")
    public Map<String, Object> handleDeleteRequest(int id) {

        Map<String, Object> attrs = new HashMap<>();

        boolean isExists = projectRolesService.isExistsById(id);
        attrs.put("isExists", isExists);
        String message = null;
        if (!isExists) {
            message = "Проектная роль сотрудников с таким ID не существует.";
        } else {
            try {
                projectRolesService.deleteById(id);
            } catch (TransactionSystemException e) {
                Throwable ex = SQLExceptionParser.getUnwrappedPSQLException(e);
                if (ex != null) {
                    ex.printStackTrace();
                    message = "Невозможно удалить проектную роль, т.к. существуют сотрудники, занимающие ее.";
                }
            }
        }

        if (message == null) {
            attrs.put("info", "Проектная роль сотрудников с ID " + id + " удален.");
        } else {
            attrs.put("error", message);
        }

        return attrs;
    }
}
