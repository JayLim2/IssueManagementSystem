package org.sergei.komarov.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "issue_workflow_statuses")
@NoArgsConstructor
@Getter
@Setter
public class IssueWorkflowStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workflow_status_id_seq")
    @SequenceGenerator(name = "workflow_status_id_seq")
    @Column(name = "workflow_status_id")
    private Integer id;

    @Column(unique = true, nullable = false)
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

    @Override
    public String toString() {
        return name;
    }
}
