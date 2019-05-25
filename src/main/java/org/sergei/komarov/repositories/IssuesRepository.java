package org.sergei.komarov.repositories;

import org.sergei.komarov.models.Issue;
import org.sergei.komarov.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssuesRepository extends JpaRepository<Issue, Integer> {
    List<Issue> findByProject(Project project);
}
