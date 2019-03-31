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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_priority_id_seq")
    @SequenceGenerator(name = "issue_priority_id_seq")
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "priority")
    private List<Issue> issues;
}