package org.sergei.komarov.controllers.handbooks;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/handbook/userRoles")
public class UserRolesHandbookController {

    @RequestMapping("/userRoles")
    public String getUserRolesHandbookViewPage() {
        return "userRoles";
    }

    @RequestMapping("/userRoles/add")
    public String getUserRolesHandbookAddPage() {
        return "userRoles";
    }

    @RequestMapping("/userRoles/edit")
    public String getUserRolesHandbookEditPage() {
        return "userRoles";
    }

    @RequestMapping("/userRoles/delete")
    public String getUserRolesHandbookDeletePage() {
        return "userRoles";
    }
}
