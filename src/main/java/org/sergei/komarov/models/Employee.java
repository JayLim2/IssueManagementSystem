package org.sergei.komarov.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employees")
@NoArgsConstructor
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "employee_id_seq")
    @Column(name = "employee_id")
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @ManyToOne
    @JoinColumn(nullable = false)
    private EmployeePosition position;

    @OneToOne(cascade = CascadeType.ALL)
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

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
