package org.sergei.komarov.controllers.handbooks;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/handbook")
public class ProjectTypesHandbookController {

    @RequestMapping("/projectTypes")
    public String getProjectTypesHandbookViewPage() {
        return "projectTypes";
    }

    @RequestMapping("/projectTypes/add")
    public String getProjectTypesHandbookAddPage() {
        return "projectTypes";
    }

    @RequestMapping("/projectTypes/edit")
    public String getProjectTypesHandbookEditPage() {
        return "projectTypes";
    }

    @RequestMapping("/projectTypes/delete")
    public String getProjectTypesHandbookDeletePage() {
        return "projectTypes";
    }
}
