package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//@Entity
//@Table(name = "issue_actions")
@NoArgsConstructor
@Data
public class IssueAction {
    private Issue issue;
    private IssueActionType actionType;
    private LocalDateTime date;
}
