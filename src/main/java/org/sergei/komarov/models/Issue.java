package org.sergei.komarov.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "issues")
@NoArgsConstructor
@Getter
@Setter
@Cacheable(false)
public class Issue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_id_seq")
    @SequenceGenerator(name = "issue_id_seq")
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "created", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdDateTime;

    @Column(name = "closed", columnDefinition = "TIMESTAMP")
    private LocalDateTime closedDateTime;

    @Column(name = "updated", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedDateTime;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "version_id")
    private ProjectVersion version;

    @ManyToOne
    @JoinColumn(name = "priority_id", nullable = false)
    private IssuePriority priority;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private IssueType type;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private IssueWorkflowStatus status;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private Employee assignee;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Employee creator;

    @ManyToOne
    @JoinColumn(name = "parent")
    private Issue parent;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
    private List<Issue> children;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "issue")
    private List<IssueAction> issueActions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "issue")
    private List<TimeSheet> associatedTimeSheets;

    @Override
    public String toString() {
        return title;
    }
}
