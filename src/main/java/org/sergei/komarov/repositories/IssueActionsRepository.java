package org.sergei.komarov.repositories;

import org.sergei.komarov.models.Issue;
import org.sergei.komarov.models.IssueAction;
import org.sergei.komarov.models.keys.IssueActionKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueActionsRepository extends JpaRepository<IssueAction, IssueActionKey> {
    List<IssueAction> findByIssue(Issue issue);
}
