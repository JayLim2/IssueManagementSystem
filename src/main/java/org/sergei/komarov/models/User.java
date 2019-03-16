package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
//@Table(name = "users")
@NoArgsConstructor
@Data
public class User {
    //@Id
    private String login;
    private String password;
    private Employee employee;
    private UserRole role;
}
