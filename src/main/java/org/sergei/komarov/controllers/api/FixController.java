package org.sergei.komarov.controllers.api;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.*;
import org.sergei.komarov.services.IssuePrioritiesService;
import org.sergei.komarov.services.IssueTypesService;
import org.sergei.komarov.services.UsersService;
import org.sergei.komarov.services.WorkflowStatusesService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
@RequestMapping("/fix")
public class FixController {

    private final IssuePrioritiesService issuePrioritiesService;
    private final IssueTypesService issueTypesService;
    private final WorkflowStatusesService workflowStatusesService;
    private final UsersService usersService;
    private final BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/createDefaultUser")
    public void createDefaultUser() {
        User user = new User();
        user.setRole(UserRole.ADMIN);
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

        List<WorkflowStatus> workflowStatuses = Stream.of(
                "Open", "In assessment", "Assessed", "In progress",
                "In build", "Ready For Testing", "Tested", "Resolved",
                "Implemented", "Closed"
        ).map(workflow -> {
            WorkflowStatus workflowStatus = new WorkflowStatus();
            workflowStatus.setName(workflow);
            return workflowStatus;
        }).collect(Collectors.toList());
        workflowStatusesService.saveAll(workflowStatuses);

        createDefaultUser();
    }
}
