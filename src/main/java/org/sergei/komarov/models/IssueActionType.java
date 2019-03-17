package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "issue_action_types")
@NoArgsConstructor
@Data
public class IssueActionType {
    @Id
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    private List<IssueAction> actions;
}