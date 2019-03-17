package org.sergei.komarov.models.keys;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class CommentKey implements Serializable {
    @Column(name = "issue_id")
    private int issue;

    @Column(name = "employee_id")
    private int employee;

    @Basic
    private LocalDateTime published;
}