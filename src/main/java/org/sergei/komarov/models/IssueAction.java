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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @Id
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Id
    @JoinColumn(nullable = false)
    private LocalDateTime date;

    @Column(name = "service_comment")
    private ServiceComment serviceComment;

    @Column(name = "employee_comment")
    private String employeeComment;
}
