package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_roles")
@NoArgsConstructor
@Data
public class UserRole {
    @Id
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private List<User> users;
}
