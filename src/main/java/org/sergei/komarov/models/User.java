package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
public class User {
    @Id
    private String login;

    private String password;

    @OneToOne(mappedBy = "associatedUser")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "role_name")
    private UserRole role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Filter> savedFilters;
}
