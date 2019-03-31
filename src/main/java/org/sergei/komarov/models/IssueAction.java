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
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @Id
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Id
    @JoinColumn(nullable = false)
    private LocalDateTime date;

    private String comment;
}
