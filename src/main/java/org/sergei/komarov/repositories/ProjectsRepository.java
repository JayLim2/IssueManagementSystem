package org.sergei.komarov.repositories;

import org.sergei.komarov.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectsRepository extends JpaRepository<Project, Integer> {
}
