package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.sergei.komarov.models.keys.IssueActionKey;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "issue_actions")
@IdClass(IssueActionKey.class)
@NoArgsConstructor
@Data
public class IssueAction {
    @Id
    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @Id
    @ManyToOne
    private IssueActionType type;

    @Id
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
