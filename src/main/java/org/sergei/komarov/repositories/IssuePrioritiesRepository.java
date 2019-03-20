package org.sergei.komarov.repositories;

import org.sergei.komarov.models.IssuePriority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuePrioritiesRepository extends JpaRepository<IssuePriority, Integer> {

}
