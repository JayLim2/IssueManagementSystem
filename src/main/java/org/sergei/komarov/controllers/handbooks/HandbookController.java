package org.sergei.komarov.controllers.handbooks;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface HandbookController {

    //view
    String getViewPage(Model model);

    //add
    String getAddPage();

    String handleAddRequest(Model model, @RequestParam String name);

    //edit
    String getEditPage(Model model, @PathVariable int id);

    String handleEditRequest(Model model, @PathVariable int id, @RequestParam String name);

    //delete
    String handleDeleteRequest(Model model, @PathVariable int id);
}