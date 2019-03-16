package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
//@Table(name = "issue_workflow_statuses")
@NoArgsConstructor
@Data
public class IssueWorkflowStatus {
    //@Id
    private String statusTitle;
}
