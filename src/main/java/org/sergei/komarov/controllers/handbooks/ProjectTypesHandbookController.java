package org.sergei.komarov.controllers.handbooks;

import org.sergei.komarov.models.ProjectType;
import org.sergei.komarov.services.ProjectTypesService;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/handbook/projectTypes")
public class ProjectTypesHandbookController implements HandbookController {

    private final ProjectTypesService projectTypesService;

    public ProjectTypesHandbookController(ProjectTypesService projectTypesService) {
        this.projectTypesService = projectTypesService;
    }

    @Override
    @RequestMapping("/view")
    public String getViewPage(Model model) {

        List<ProjectType> projectTypes = projectTypesService.getAll();
        model.addAttribute("entities", projectTypes);

        return "projectTypes";
    }

    @Override
    @GetMapping("/add")
    public String getAddPage(Model model) {
        return "addProjectType";
    }

    @Override
    @PostMapping("/add")
    public String handleAddRequest(Model model, @RequestParam String name) {
        Map<String, Object> attrs = new HashMap<>();

        projectTypesService.validateAndSave(attrs, name);
        model.addAllAttributes(attrs);

        return "addProjectType";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String getEditPage(Model model, @PathVariable int id) {
        Map<String, Object> attrs = new HashMap<>();

        if (!projectTypesService.isExistsById(id)) {
            attrs.put("error", "Тип проектов с таким ID не существует.");
        } else {
            ProjectType projectType = projectTypesService.getById(id);
            attrs.put("entity", projectType);
        }
        model.addAllAttributes(attrs);

        return "editProjectType";
    }

    @Override
    @PostMapping("/edit/{id}")
    public String handleEditRequest(Model model, @PathVariable int id, @RequestParam String name) {
        Map<String, Object> attrs = new HashMap<>();

        projectTypesService.validateAndUpdate(attrs, id, name);
        model.addAllAttributes(attrs);

        return "editProjectType";
    }

    @Override
    @PostMapping("/delete/{id}")
    public String handleDeleteRequest(Model model, @PathVariable int id) {
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
                    message = ex.getMessage();
                }
            }
        }

        if (message == null) {
            attrs.put("info", "Тип проектов с ID " + id + " удален.");
        } else {
            attrs.put("error", message);
        }

        model.addAllAttributes(attrs);

        return "delete";
    }
}
