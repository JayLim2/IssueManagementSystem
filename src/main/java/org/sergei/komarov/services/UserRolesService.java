package org.sergei.komarov.services;

import org.sergei.komarov.models.UserRole;
import org.sergei.komarov.repositories.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRolesService {
    private final UserRolesRepository userRolesRepository;

    @Autowired
    public UserRolesService(UserRolesRepository userRolesRepository) {
        this.userRolesRepository = userRolesRepository;
    }

    public void save(UserRole role) {
        userRolesRepository.save(role);
    }

    public List<UserRole> getAll() {
        return userRolesRepository.findAll();
    }
}
