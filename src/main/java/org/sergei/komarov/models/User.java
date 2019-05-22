package org.sergei.komarov.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    private String login;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "associatedUser", cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", unique = true)
    private Employee employee;

    @Column(name = "role_name", nullable = false)
    private UserRole role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Filter> savedFilters;
}
