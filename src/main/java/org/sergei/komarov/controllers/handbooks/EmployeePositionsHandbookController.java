package org.sergei.komarov.controllers.handbooks;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/handbook/employeePositions")
public class EmployeePositionsHandbookController implements HandbookController {

    @Override
    @RequestMapping("/view")
    public String getViewPage(Model model) {
        return null;
    }

    @Override
    @GetMapping("/add")
    public String getAddPage() {
        return null;
    }

    @Override
    @PostMapping("/add")
    public String handleAddRequest(Model model, @RequestParam String name) {
        return null;
    }

    @Override
    @GetMapping("/edit/{id}")
    public String getEditPage(Model model, @PathVariable int id) {
        return null;
    }

    @Override
    @PostMapping("/edit/{id}")
    public String handleEditRequest(Model model, @PathVariable int id, @RequestParam String name) {
        return null;
    }

    @Override
    @GetMapping("/delete/{id}")
    public String handleDeleteRequest(Model model, @PathVariable int id) {
        return null;
    }
}
