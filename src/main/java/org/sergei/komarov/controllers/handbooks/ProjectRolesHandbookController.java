package org.sergei.komarov.controllers.handbooks;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/handbook/projectRoles")
public class ProjectRolesHandbookController {

    @RequestMapping("/projectRoles")
    public String getProjectRolesHandbookViewPage() {
        return "projectRoles";
    }

    @RequestMapping("/projectRoles/add")
    public String getProjectRolesHandbookAddPage() {
        return "projectRoles";
    }

    @RequestMapping("/projectRoles/edit")
    public String getProjectRolesHandbookEditPage() {
        return "projectRoles";
    }

    @RequestMapping("/projectRoles/delete")
    public String getProjectRolesHandbookDeletePage() {
        return "projectRoles";
    }
}
