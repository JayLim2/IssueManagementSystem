package org.sergei.komarov.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "issues")
@NoArgsConstructor
@Data
public class Issue {
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
