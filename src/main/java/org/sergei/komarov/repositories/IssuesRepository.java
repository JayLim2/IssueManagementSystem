package org.sergei.komarov.repositories;

import org.sergei.komarov.models.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuesRepository extends JpaRepository<Issue, Integer> {
}
