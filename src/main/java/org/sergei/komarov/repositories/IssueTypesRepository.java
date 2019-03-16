package org.sergei.komarov.repositories;

import org.sergei.komarov.models.IssueType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueTypesRepository extends JpaRepository<IssueType, Integer> {
}
