package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "issue_workflow_statuses")
@NoArgsConstructor
@Data
public class IssueWorkflowStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workflow_status_id")
    private Integer id;

    @Column(unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
    private List<Issue> issues;

    @ManyToMany
    @JoinTable(
            name = "workflow_statuses_by_issue_types",
            joinColumns = @JoinColumn(name = "workflow_status_id"),
            inverseJoinColumns = @JoinColumn(name = "issue_type_id")
    )
    private List<IssueType> issueTypes;
}
