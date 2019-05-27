package org.sergei.komarov.repositories;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.Issue;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class FiltersRepository {

    private final EntityManager entityManager;

    private static final DateTimeFormatter DATE_FORMATER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public List<Issue> searchIssues(String searchQuery,
                                    String dueDateFromStr,
                                    String dueDateToStr,
                                    String createdDateFromStr,
                                    String createdDateToStr,
                                    String updatedDateFromStr,
                                    String updatedDateToStr,
                                    String closedDateFromStr,
                                    String closedDateToStr,
                                    List<Integer> projectsIds,
                                    List<Integer> typesIds,
                                    List<Integer> statusesIds,
                                    List<Integer> prioritiesIds,
                                    List<Integer> assigneesIds,
                                    List<Integer> creatorsIds) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Issue> query = criteriaBuilder.createQuery(Issue.class);

        Root<Issue> issue = query.from(Issue.class);
        List<Predicate> predicates = new ArrayList<>();

        Predicate temp1 = null;
        Predicate temp2 = null;

        if (searchQuery != null) {
            temp1 = criteriaBuilder.like(issue.get("title"), "%" + searchQuery + "%");
            temp2 = criteriaBuilder.like(issue.get("description"), "%" + searchQuery + "%");
            predicates.add(criteriaBuilder.or(temp1, temp2));
        }

        //dates
        addDatePredicate(criteriaBuilder, issue, predicates, dueDateFromStr, dueDateToStr, "dueDate");
        addDatePredicate(criteriaBuilder, issue, predicates, createdDateFromStr, createdDateToStr, "createdDateTime");
        addDatePredicate(criteriaBuilder, issue, predicates, updatedDateFromStr, updatedDateToStr, "updatedDateTime");
        addDatePredicate(criteriaBuilder, issue, predicates, closedDateFromStr, closedDateToStr, "closedDateTime");

        //projects
        if (projectsIds != null && projectsIds.size() > 0) {
            CriteriaBuilder.In<Integer> projectIdsIn = criteriaBuilder.in(issue.get("project"));
            for (Integer id : projectsIds) {
                projectIdsIn.value(id);
            }
            predicates.add(projectIdsIn);
        }

        //issue types
        if (typesIds != null && typesIds.size() > 0) {
            CriteriaBuilder.In<Integer> typesIdsIn = criteriaBuilder.in(issue.get("type"));
            for (Integer id : typesIds) {
                typesIdsIn.value(id);
            }
            predicates.add(typesIdsIn);
        }

        //issue priorities
        if (prioritiesIds != null && prioritiesIds.size() > 0) {
            CriteriaBuilder.In<Integer> prioritiesIdsIn = criteriaBuilder.in(issue.get("priority"));
            for (Integer id : prioritiesIds) {
                prioritiesIdsIn.value(id);
            }
            predicates.add(prioritiesIdsIn);
        }

        //issue statuses
        if (statusesIds != null && statusesIds.size() > 0) {
            CriteriaBuilder.In<Integer> statusesIdsIn = criteriaBuilder.in(issue.get("status"));
            for (Integer id : statusesIds) {
                statusesIdsIn.value(id);
            }
            predicates.add(statusesIdsIn);
        }

        //assignees
        if (assigneesIds != null && assigneesIds.size() > 0) {
            CriteriaBuilder.In<Integer> assigneesIdsIn = criteriaBuilder.in(issue.get("assignee"));
            for (Integer id : assigneesIds) {
                assigneesIdsIn.value(id);
            }
            predicates.add(assigneesIdsIn);
        }

        //creators
        if (creatorsIds != null && creatorsIds.size() > 0) {
            CriteriaBuilder.In<Integer> creatorsIdsIn = criteriaBuilder.in(issue.get("creator"));
            for (Integer id : creatorsIds) {
                creatorsIdsIn.value(id);
            }
            predicates.add(creatorsIdsIn);
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

    // FIXME: 26.05.2019 
    private void addDatePredicate(CriteriaBuilder criteriaBuilder, Root<Issue> issue,
                                  List<Predicate> predicates,
                                  String fromStr, String toStr, String fieldName) {

        Predicate predicate;

        if (fromStr != null && !fromStr.isEmpty()) {
            if ("dueDate".equals(fieldName)) {
                LocalDate from = LocalDate.parse(fromStr, DATE_FORMATER).minusDays(1);
                predicate = criteriaBuilder.greaterThan(issue.get(fieldName), from);
            } else {
                LocalDateTime from = LocalDateTime.of(
                        LocalDate.parse(fromStr, DATE_FORMATER).minusDays(1),
                        LocalTime.of(23, 59, 59)
                );

                System.out.println("\n\n\n" + from + "\n\n\n");

                predicate = criteriaBuilder.greaterThan(issue.get(fieldName), from);
            }
            predicates.add(predicate);
        }

        if (toStr != null && !toStr.isEmpty()) {
            if ("dueDate".equals(fieldName)) {
                LocalDate to = LocalDate.parse(toStr, DATE_FORMATER).plusDays(1);
                predicate = criteriaBuilder.lessThan(issue.get(fieldName), to);
            } else {
                LocalDateTime to = LocalDateTime.of(
                        LocalDate.parse(toStr, DATE_FORMATER).plusDays(1),
                        LocalTime.of(0, 0)
                );
                predicate = criteriaBuilder.lessThan(issue.get(fieldName), to);
            }
            predicates.add(predicate);
        }
    }
}
