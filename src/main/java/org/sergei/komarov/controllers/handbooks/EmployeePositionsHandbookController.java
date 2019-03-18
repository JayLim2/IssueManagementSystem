package org.sergei.komarov.controllers.handbooks;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/handbook")
public class EmployeePositionsHandbookController {

    @RequestMapping("/employeePositions")
    public String getEmployeePositionsHandbookViewPage() {

        return "employeePositions";
    }

    @RequestMapping("/employeePositions/add")
    public String getEmployeePositionsHandbookAddPage() {

        return "employeePositions";
    }

    @RequestMapping("/employeePositions/edit")
    public String getEmployeePositionsHandbookEditPage() {

        return "employeePositions";
    }

    @RequestMapping("/employeePositions/delete")
    public String getEmployeePositionsHandbookDeletePage() {

        return "employeePositions";
    }

}
