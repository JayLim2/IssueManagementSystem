package org.sergei.komarov.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "user_roles")
public class UserRole {
    private String name;
    private List<User> users;
}
