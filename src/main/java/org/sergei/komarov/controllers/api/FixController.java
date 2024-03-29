package org.sergei.komarov.controllers.api;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.*;
import org.sergei.komarov.services.*;
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
    private final IssueWorkflowStatusesService workflowStatusesService;
    private final UsersService usersService;
    private final EmployeePositionsService employeePositionsService;
    private final EmployeesService employeesService;
    private final BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/createDefaultUser")
    public void createDefaultUser() {
        EmployeePosition position = new EmployeePosition();
        position.setName("System");
        position = employeePositionsService.saveAndGet(position);

        Employee employee = new Employee();
        employee.setFirstName("superuser");
        employee.setLastName("account");
        employee.setPosition(position);

        User user = new User();
        user.setRole(UserRole.ADMIN);
        user.setLogin("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setEmployee(employee);

        employee.setAssociatedUser(user);
        user.setEmployee(employee);

        employeesService.save(employee);
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
            IssueWorkflowStatus workflowStatus = new IssueWorkflowStatus();
            workflowStatus.setName(workflow);
            return workflowStatus;
        }).collect(Collectors.toList());
        workflowStatusesService.saveAll(workflowStatuses);

        createDefaultUser();
    }
}
