package org.sergei.komarov.controllers.handbooks;

import org.sergei.komarov.models.ProjectRole;
import org.sergei.komarov.services.ProjectRolesService;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/handbook/projectRoles")
public class ProjectRolesHandbookController implements HandbookController {

    private final ProjectRolesService projectRolesService;

    public ProjectRolesHandbookController(ProjectRolesService projectRolesService) {
        this.projectRolesService = projectRolesService;
    }

    @Override
    @RequestMapping("/view")
    public String getViewPage(Model model) {
        List<ProjectRole> projectRoles = projectRolesService.getAll();
        Map<String, List<ProjectRole>> params = new HashMap<>();
        params.put("entities", projectRoles);
        model.addAllAttributes(params);

        return "projectRoles";
    }

    @Override
    @GetMapping("/add")
    public String getAddPage(Model model) {

        return "addProjectRole";
    }

    @Override
    @PostMapping("/add")
    public String handleAddRequest(Model model, @RequestParam String name) {
        Map<String, Object> attrs = new HashMap<>();

        projectRolesService.validateAndSave(attrs, name);
        model.addAllAttributes(attrs);

        return "addProjectRole";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String getEditPage(Model model, @PathVariable int id) {
        Map<String, Object> attrs = new HashMap<>();

        if (!projectRolesService.isExistsById(id)) {
            attrs.put("error", "Проектная роль сотрудников с таким ID не существует.");
        } else {
            ProjectRole projectRole = projectRolesService.getById(id);
            attrs.put("entity", projectRole);
        }
        model.addAllAttributes(attrs);

        return "editProjectRole";
    }

    @Override
    @PostMapping("/edit/{id}")
    public String handleEditRequest(Model model, @PathVariable int id, @RequestParam String name) {
        Map<String, Object> attrs = new HashMap<>();

        projectRolesService.validateAndUpdate(attrs, id, name);
        model.addAllAttributes(attrs);

        return "editProjectRole";
    }

    @Override
    @GetMapping("/delete/{id}")
    public String handleDeleteRequest(Model model, @PathVariable int id) {

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
                    message = ex.getMessage();
                }
            }
        }

        if (message == null) {
            attrs.put("info", "Проектная роль сотрудников с ID " + id + " удален.");
        } else {
            attrs.put("error", message);
        }

        model.addAllAttributes(attrs);

        return "editProjectRole";
    }
}
