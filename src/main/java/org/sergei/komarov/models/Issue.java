package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "issues")
@NoArgsConstructor
@Data
public class Issue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    @Column(name = "created")
    private LocalDateTime createdDateTime;

    @Column(name = "closed")
    private LocalDateTime closedDateTime;

    @Column(name = "updated")
    private LocalDateTime updatedDateTime;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private ProjectComponent component;

    @ManyToOne
    @JoinColumn(name = "version_id")
    private ProjectVersion version;

    @ManyToOne
    @JoinColumn(name = "priority_id")
    private IssuePriority priority;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private IssueType type;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private IssueWorkflowStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "issue")
    private List<IssueAction> issueActions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "issue")
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "assigned_employees",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "issue")
    private List<TimeSheet> associatedTimeSheets;
}
