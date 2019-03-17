package org.sergei.komarov.models.keys;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class IssueActionKey implements Serializable {
    @Column(name = "issue_id")
    private int issue;

    @Column(name = "type")
    private String type;

    @Basic
    private LocalDateTime date;
}