package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employees")
@NoArgsConstructor
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "employee_id_seq")
    @Column(name = "employee_id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    private EmployeePosition position;

    @OneToOne
    @JoinColumn(name = "associated_user")
    private User associatedUser;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "assigned_employees",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "issue_id")
    )
    private List<Issue> issues;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<IssueAction> issueActions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<ProjectTeam> projectTeams;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<TimeSheet> timeSheets;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<Comment> comments;
}
