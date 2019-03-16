package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "issue_workflow_statuses")
@NoArgsConstructor
@Data
public class IssueWorkflowStatus {
    @Id
    private String statusTitle;
}
