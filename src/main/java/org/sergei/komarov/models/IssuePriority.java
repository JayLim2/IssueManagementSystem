package org.sergei.komarov.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "issue_priorities")
@NoArgsConstructor
@Getter
@Setter
public class IssuePriority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_priority_id_seq")
    @SequenceGenerator(name = "issue_priority_id_seq")
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "priority")
    private List<Issue> issues;

    @Override
    public String toString() {
        return name;
    }
}