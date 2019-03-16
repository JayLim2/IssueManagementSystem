package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//@Entity
//@Table(name = "user_roles")
@NoArgsConstructor
@Data
public class UserRole {
    private String name;
    private List<User> users;
}
