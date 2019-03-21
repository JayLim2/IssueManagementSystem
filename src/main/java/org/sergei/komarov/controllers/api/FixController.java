package org.sergei.komarov.controllers.api;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.*;
import org.sergei.komarov.services.IssuePrioritiesService;
import org.sergei.komarov.services.IssueTypesService;
import org.sergei.komarov.services.IssueWorkflowStatusesService;
import org.sergei.komarov.services.UsersService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
@RequestMapping("/fix")
public class FixController {

    private final IssuePrioritiesService issuePrioritiesService;
    private final IssueTypesService issueTypesService;
    private final IssueWorkflowStatusesService issueWorkflowStatusesService;
    private final UsersService usersService;
    private final BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/createDefaultUser")
    public void createDefaultUser() {
        User user = new User();
        UserRole userRole = new UserRole();
        userRole.setName("ADMIN");
        user.setRole(userRole);
        user.setLogin("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        usersService.save(user);
    }

    @RequestMapping("/initDB")
    public void initDB() {
        List<IssuePriority> issuePriorities = Stream.of(
                "Minor", "Normal", "Major", "Critical", "Blocker"
        ).map(priority -> {
            IssuePriority issuePriority = new IssuePriority();
            issuePriority.setName(priority);
            return issuePriority;
        }).collect(Collectors.toList());
        issuePrioritiesService.saveAll(issuePriorities);

        List<IssueType> issueTypes = Stream.of(
                "Work Item", "Bug", "Dev Task", "Design Task",
                "Question", "Information", "Test Run", "Test Case"
        ).map(type -> {
            IssueType issueType = new IssueType();
            issueType.setName(type);
            return issueType;
        }).collect(Collectors.toList());
        issueTypesService.saveAll(issueTypes);

        List<IssueWorkflowStatus> workflowStatuses = Stream.of(
                "Open", "In assessment", "Assessed", "In progress",
                "In build", "Ready For Testing", "Tested", "Resolved",
                "Implemented", "Closed"
        ).map(workflow -> {
            IssueWorkflowStatus issueWorkflowStatus = new IssueWorkflowStatus();
            issueWorkflowStatus.setName(workflow);
            return issueWorkflowStatus;
        }).collect(Collectors.toList());
        issueWorkflowStatusesService.saveAll(workflowStatuses);

        createDefaultUser();
    }
}
