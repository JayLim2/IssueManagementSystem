package org.sergei.komarov.repositories;

import org.sergei.komarov.models.Employee;
import org.sergei.komarov.models.Issue;
import org.sergei.komarov.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IssuesRepository extends JpaRepository<Issue, Integer> {
    List<Issue> findByProject(Project project);

    @Query("SELECT issue FROM Issue issue WHERE issue.assignee = :employee OR issue.creator = :employee")
    List<Issue> findByAssigneeOrCreator(@Param("employee") Employee employee);

    //by projects
    @Query("SELECT issue FROM Issue issue WHERE :now > issue.dueDate AND issue.project = :project")
    List<Issue> findOverdueIssuesByProject(@Param("now") LocalDate now, @Param("project") Project project);

    @Query("SELECT issue FROM Issue issue WHERE issue.dueDate = null AND issue.project = :project")
    List<Issue> findIssuesWithoutDueDateByProject(@Param("project") Project project);

    @Query("SELECT issue FROM Issue issue WHERE issue.dueDate <> null AND :date <= issue.dueDate AND issue.project = :project")
    List<Issue> findIssuesWithExpiringDueDateByProject(@Param("date") LocalDate date, @Param("project") Project project);

    @Query("SELECT count(issue) FROM Issue issue WHERE :now > issue.dueDate AND issue.project = :project")
    int countOverdueIssuesByProject(@Param("now") LocalDate now, @Param("project") Project project);

    @Query("SELECT count(issue) FROM Issue issue WHERE issue.dueDate = null AND issue.project = :project")
    int countIssuesWithoutDueDateByProject(@Param("project") Project project);

    @Query("SELECT count(issue) FROM Issue issue WHERE issue.dueDate <> null AND :date <= issue.dueDate AND issue.project = :project")
    int countIssuesWithExpiringDueDateByProject(@Param("date") LocalDate date, @Param("project") Project project);

    //by employees
    @Query("SELECT issue FROM Issue issue WHERE :now > issue.dueDate AND issue.assignee = :employee")
    List<Issue> findOverdueIssuesByEmployee(@Param("now") LocalDate now, @Param("employee") Employee employee);

    @Query("SELECT issue FROM Issue issue WHERE issue.dueDate = null AND issue.assignee = :employee")
    List<Issue> findIssuesWithoutDueDateByEmployee(@Param("employee") Employee employee);

    @Query("SELECT issue FROM Issue issue WHERE issue.dueDate <> null AND :date <= issue.dueDate AND issue.assignee = :employee")
    List<Issue> findIssuesWithExpiringDueDateByEmployee(@Param("date") LocalDate date, @Param("employee") Employee employee);

    @Query("SELECT count(issue) FROM Issue issue WHERE :now > issue.dueDate AND (issue.assignee = :employee OR issue.creator = :employee)")
    int countOverdueIssuesByEmployee(@Param("now") LocalDate now, @Param("employee") Employee employee);

    @Query("SELECT count(issue) FROM Issue issue WHERE issue.dueDate = null AND (issue.assignee = :employee OR issue.creator = :employee)")
    int countIssuesWithoutDueDateByEmployee(@Param("employee") Employee employee);

    @Query("SELECT count(issue) FROM Issue issue WHERE issue.dueDate <> null AND :date <= issue.dueDate AND (issue.assignee = :employee OR issue.creator = :employee)")
    int countIssuesWithExpiringDueDateByEmployee(@Param("date") LocalDate date, @Param("employee") Employee employee);

}
