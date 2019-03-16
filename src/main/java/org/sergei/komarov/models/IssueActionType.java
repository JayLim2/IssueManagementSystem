package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//@Entity
//@Table(name = "issue_action_types")
@NoArgsConstructor
@Data
public class IssueActionType {
    //@Id
    private String title;
    private List<IssueAction> actions;
}