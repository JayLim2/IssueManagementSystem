package org.sergei.komarov.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "issue_types")
@NoArgsConstructor
@Getter
@Setter
public class IssueType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_type_id_seq")
    @SequenceGenerator(name = "issue_type_id_seq")
    @Column(name = "issue_type_id")
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    private List<Issue> issues;

    @ManyToMany
    @JoinTable(
            name = "workflow_statuses_by_issue_types",
            joinColumns = @JoinColumn(name = "issue_type_id"),
            inverseJoinColumns = @JoinColumn(name = "workflow_status_id")
    )
    private List<IssueWorkflowStatus> workflowStatuses;

    @Override
    public String toString() {
        return name;
    }
}
