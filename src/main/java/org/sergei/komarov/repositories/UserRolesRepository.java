package org.sergei.komarov.repositories;

import org.sergei.komarov.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesRepository extends JpaRepository<UserRole, String> {
}
