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
    private String statusTitle;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
    private List<Issue> issues;
}
