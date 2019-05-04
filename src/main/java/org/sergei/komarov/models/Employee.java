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

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @ManyToOne
    @JoinColumn(nullable = false)
    private EmployeePosition position;

    @OneToOne
    @JoinColumn(name = "associated_user", nullable = false)
    private User associatedUser;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assignee")
    private List<Issue> assignedIssues;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
    private List<Issue> createdIssues;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<IssueAction> issueActions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<ProjectTeamMember> projectTeams;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<TimeSheet> timeSheets;
}
