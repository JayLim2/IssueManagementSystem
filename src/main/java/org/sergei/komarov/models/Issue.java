package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//@Entity
//@Table(name = "issues")
@NoArgsConstructor
@Data
public class Issue {
    //@Id
    private int id;
    private String title;
    private String description;
    private LocalDateTime createdDateTime;
    private LocalDateTime closedDateTime;
    private LocalDateTime updatedDateTime;
    private LocalDate dueDate;
    private Project project;
    private IssuePriority priority;
    private IssueType type;
    private IssueWorkflowStatus status;
    private List<IssueAction> issueActions;
    private List<Comment> comments;
    private List<Employee> employees;
    private List<TimeSheet> associatedTimeSheets;
}
