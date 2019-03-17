package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "issue_priorities")
@NoArgsConstructor
@Data
public class IssuePriority {
    @Id
    private String priorityTitle;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "priority")
    private List<Issue> issues;
}