package org.sergei.komarov.repositories;

import org.sergei.komarov.models.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTypesRepository extends JpaRepository<ProjectType, Integer> {
}
