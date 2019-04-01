package org.sergei.komarov.repositories;

import org.sergei.komarov.models.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRolesRepository extends JpaRepository<ProjectRole, Integer> {
}
