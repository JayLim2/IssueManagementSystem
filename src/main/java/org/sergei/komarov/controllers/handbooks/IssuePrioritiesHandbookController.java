package org.sergei.komarov.controllers.handbooks;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/handbook")
public class IssuePrioritiesHandbookController {

    @RequestMapping("/issuePriorities")
    public String getIssuePrioritiesHandbookViewPage(Model model) {

        List<String> pr = Arrays.asList(
                "Minor", "Normal", "Major", "Critical"
        );

        Map<String, List<String>> params = new HashMap<>();
        params.put("pr", pr);
        model.addAllAttributes(params);

        return "issuePriorities";
    }

    @RequestMapping("/issuePriorities/add")
    public String getIssuePrioritiesHandbookAddPage() {

        return "issuePriorities";
    }

    @RequestMapping("/issuePriorities/edit")
    public String getIssuePrioritiesHandbookEditPage() {

        return "issuePriorities";
    }

    @RequestMapping("/issuePriorities/delete")
    public String getIssuePrioritiesHandbookDeletePage() {

        return "issuePriorities";
    }
}
