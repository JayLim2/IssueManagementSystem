package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//@Entity
//@Table(name = "employees")
@NoArgsConstructor
@Data
public class Employee {
    //@Id
    private int id;
    private String firstName;
    private String lastName;
    private EmployeePosition position;
    private User associatedUser;
    private List<Issue> issues;
    private List<IssueAction> issueActions;
    private List<Project> projects;
    private List<TimeSheet> timeSheets;
    private List<Comment> comments;
}
