package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "issue_workflow_statuses")
@NoArgsConstructor
@Data
public class WorkflowStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workflow_status_id_seq")
    @SequenceGenerator(name = "workflow_status_id_seq")
    @Column(name = "workflow_status_id")
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
    private List<Issue> issues;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
    private List<Project> projects;

    @ManyToMany
    @JoinTable(
            name = "workflow_statuses_by_issue_types",
            joinColumns = @JoinColumn(name = "workflow_status_id"),
            inverseJoinColumns = @JoinColumn(name = "issue_type_id")
    )
    private List<IssueType> issueTypes;
}
