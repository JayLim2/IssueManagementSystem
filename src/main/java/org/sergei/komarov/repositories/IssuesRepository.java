package org.sergei.komarov.repositories;

import org.sergei.komarov.models.Issue;
import org.sergei.komarov.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IssuesRepository extends JpaRepository<Issue, Integer> {
    List<Issue> findByProject(Project project);

    @Query("SELECT issue FROM Issue issue WHERE :now > issue.dueDate AND issue.project = :project")
    List<Issue> findOverdueIssuesByProject(@Param("now") LocalDate now, @Param("project") Project project);

    @Query("SELECT issue FROM Issue issue WHERE issue.dueDate = null")
    List<Issue> findIssuesWithoutDueDateByProject(@Param("project") Project project);

    @Query("SELECT issue FROM Issue issue WHERE :date <= issue.dueDate AND issue.dueDate")
    List<Issue> findIssuesWithExpiringDueDateByProject(@Param("date") LocalDate date, @Param("project") Project project);

    @Query("SELECT count(issue) FROM Issue issue WHERE :now > issue.dueDate AND issue.project = :project")
    int countOverdueIssuesByProject(@Param("now") LocalDate now, @Param("project") Project project);

    @Query("SELECT count(issue) FROM Issue issue WHERE issue.dueDate = null")
    int countIssuesWithoutDueDateByProject(@Param("project") Project project);

    @Query("SELECT count(issue) FROM Issue issue WHERE :date <= issue.dueDate AND issue.dueDate")
    int countIssuesWithExpiringDueDateByProject(@Param("date") LocalDate date, @Param("project") Project project);

}
