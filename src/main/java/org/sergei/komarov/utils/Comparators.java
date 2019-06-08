package org.sergei.komarov.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sergei.komarov.models.Employee;
import org.sergei.komarov.models.Project;
import org.sergei.komarov.services.IssuesService;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
@AllArgsConstructor
public class Comparators {
    private final IssuesService issuesService;

    public Comparator<Project> getProjectsComparator() {
        return new ProjectsComparator();
    }

    public Comparator<Employee> getEmployeesComparator() {
        return new EmployeesComparator();
    }

    @NoArgsConstructor
    @Data
    private class ProjectsComparator implements Comparator<Project> {

        private int overdueIssuesCount1;
        private int withExpiringDueDateIssuesCount1;
        private int withoutDueDateIssuesCount1;

        private int overdueIssuesCount2;
        private int withExpiringDueDateIssuesCount2;
        private int withoutDueDateIssuesCount2;

        private void cache(Project project1, Project project2) {
            overdueIssuesCount1 = issuesService.getOverdueIssuesByProjectCount(project1);
            overdueIssuesCount2 = issuesService.getOverdueIssuesByProjectCount(project2);

            withExpiringDueDateIssuesCount1 = issuesService.getIssuesWithExpiringDueDateByProjectCount(project1);
            withExpiringDueDateIssuesCount2 = issuesService.getIssuesWithExpiringDueDateByProjectCount(project2);

            withoutDueDateIssuesCount1 = issuesService.getIssuesWithoutDueDateByProjectCount(project1);
            withoutDueDateIssuesCount2 = issuesService.getIssuesWithoutDueDateByProjectCount(project2);
        }

        @Override
        public int compare(Project o1, Project o2) {

            cache(o1, o2);

            int currentTotal = overdueIssuesCount1
                    + withExpiringDueDateIssuesCount1
                    + withoutDueDateIssuesCount1;

            int otherTotal = overdueIssuesCount2
                    + withExpiringDueDateIssuesCount2
                    + withoutDueDateIssuesCount2;

            return otherTotal - currentTotal;
        }
    }

    @NoArgsConstructor
    @Data
    private class EmployeesComparator implements Comparator<Employee> {

        private int overdueIssuesCount1;
        private int withExpiringDueDateIssuesCount1;
        private int withoutDueDateIssuesCount1;

        private int overdueIssuesCount2;
        private int withExpiringDueDateIssuesCount2;
        private int withoutDueDateIssuesCount2;

        private void cache(Employee employee1, Employee employee2) {
            overdueIssuesCount1 = issuesService.getOverdueIssuesByEmployeeCount(employee1);
            overdueIssuesCount2 = issuesService.getOverdueIssuesByEmployeeCount(employee2);

            withExpiringDueDateIssuesCount1 = issuesService.getIssuesWithExpiringDueDateByEmployeeCount(employee1);
            withExpiringDueDateIssuesCount2 = issuesService.getIssuesWithExpiringDueDateByEmployeeCount(employee2);

            withoutDueDateIssuesCount1 = issuesService.getIssuesWithoutDueDateByEmployeeCount(employee1);
            withoutDueDateIssuesCount2 = issuesService.getIssuesWithoutDueDateByEmployeeCount(employee2);
        }

        @Override
        public int compare(Employee o1, Employee o2) {

            cache(o1, o2);

            int currentTotal = overdueIssuesCount1
                    + withExpiringDueDateIssuesCount1
                    + withoutDueDateIssuesCount1;

            int otherTotal = overdueIssuesCount2
                    + withExpiringDueDateIssuesCount2
                    + withoutDueDateIssuesCount2;

            return otherTotal - currentTotal;
        }
    }
}
