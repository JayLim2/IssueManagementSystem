package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.sergei.komarov.models.keys.CommentKey;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@IdClass(CommentKey.class)
@NoArgsConstructor
@Data
public class Comment {
    @Id
    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @Id
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Id
    private LocalDateTime published;

    private String text;
}
