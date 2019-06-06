package org.sergei.komarov.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.sergei.komarov.models.Employee;
import org.sergei.komarov.models.Issue;
import org.sergei.komarov.models.Project;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportsService {
    private final EmployeesService employeesService;
    private final EmployeePositionsService employeePositionsService;

    private final ProjectsService projectsService;
    private final IssuesService issuesService;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("-yyyy-MM-dd-HH-mm-ss");
    private static final String PROJECTS_REPORT_TEMPLATE = "/static/reports/Projects.jrxml";
    private static final String EMPLOYEES_REPORT_TEMPLATE = "/static/reports/Employees.jrxml";

    public String createProjectsReport() throws JRException, IOException {
        //Data
        List<Project> projects = projectsService.getAll();
        List<ProjectsReportData> projectsReportDataList = new ArrayList<>();

        ProjectsComparator projectComparator = new ProjectsComparator();
        projects.sort(projectComparator);
        for (Project project : projects) {
            List<Issue> issuesByProject = issuesService.getByProject(project);

            int overdue = issuesService.getOverdueIssuesByProjectCount(project);
            int expiring = issuesService.getIssuesWithExpiringDueDateByProjectCount(project);
            int withoutDueDate = issuesService.getIssuesWithoutDueDateByProjectCount(project);

            for (Issue issue : issuesByProject) {
                projectsReportDataList.add(new ProjectsReportData(
                        project, issue,
                        overdue, expiring, withoutDueDate
                ));
            }
        }

        //Reporting
        return buildReport(projectsReportDataList, PROJECTS_REPORT_TEMPLATE);
    }

    public String createEmployeesReport() throws JRException, IOException {
        //Data
        List<Employee> employees = employeesService.getAll();
        List<EmployeesReportData> employeesReportDataList = new ArrayList<>();

        EmployeesComparator employeesComparator = new EmployeesComparator();
        employees.sort(employeesComparator);
        for (Employee employee : employees) {
            List<Issue> issues = issuesService.getByEmployee(employee);

            int overdue = issuesService.getOverdueIssuesByEmployeeCount(employee);
            int expiring = issuesService.getIssuesWithExpiringDueDateByEmployeeCount(employee);
            int withoutDueDate = issuesService.getIssuesWithoutDueDateByEmployeeCount(employee);

            for (Issue issue : issues) {
                employeesReportDataList.add(new EmployeesReportData(
                        employee, issue,
                        overdue, expiring, withoutDueDate
                ));
            }
        }

        //Reporting
        return buildReport(employeesReportDataList, EMPLOYEES_REPORT_TEMPLATE);
    }

    private <E> String buildReport(List<E> list, String reportPattern) throws JRException {
        JRDesignStyle normalStyle = new JRDesignStyle();
        normalStyle.setName("Tahoma");
        normalStyle.setDefault(true);
        normalStyle.setFontName("/static/fonts/Tahoma.ttf");
        normalStyle.setPdfFontName("/static/fonts/Tahoma.ttf");
        normalStyle.setPdfEncoding("Identity-H");
        normalStyle.setPdfEmbedded(true);

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);

        File reportPatternFile = new File(getClass().getResource(reportPattern).getPath());

        JasperDesign jasperDesign = JRXmlLoader.load(reportPatternFile);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                new HashMap<>(),
                beanColDataSource
        );
        jasperPrint.addStyle(normalStyle);

        String prefix = "";
        String lowerCasePattern = reportPattern.toLowerCase();
        if (lowerCasePattern.contains("employees")) {
            prefix = "employees-";
        } else if (lowerCasePattern.contains("projects")) {
            prefix = "projects-";
        }

        String reportName = prefix + "report" + dateTimeFormatter.format(LocalDateTime.now()) + ".pdf";

        JasperExportManager.exportReportToPdfFile(
                jasperPrint,
                ".\\" + reportName
        );

        return reportName;
    }

    // TODO: 07.06.2019 data duplications!
    @Data
    public class ProjectsReportData implements Comparable<ProjectsReportData> {
        private int projectId;
        private String projectTitle;
        private int issueId;
        private String issueTitle;

        private int overdueIssuesCount;
        private int withExpiringDueDateIssuesCount;
        private int withoutDueDateIssuesCount;

        public ProjectsReportData(Project project, Issue issue) {
            projectId = project.getId();
            projectTitle = project.getTitle();
            issueId = issue.getId();
            issueTitle = issue.getTitle();
            overdueIssuesCount = issuesService.getOverdueIssuesByProjectCount(project);
            withExpiringDueDateIssuesCount = issuesService.getIssuesWithExpiringDueDateByProjectCount(project);
            withoutDueDateIssuesCount = issuesService.getIssuesWithoutDueDateByProjectCount(project);
        }

        public ProjectsReportData(Project project, Issue issue,
                                  int overdueIssuesCount,
                                  int withExpiringDueDateIssuesCount,
                                  int withoutDueDateIssuesCount) {
            projectId = project.getId();
            projectTitle = project.getTitle();
            issueId = issue.getId();
            issueTitle = issue.getTitle();
            this.overdueIssuesCount = overdueIssuesCount;
            this.withExpiringDueDateIssuesCount = withExpiringDueDateIssuesCount;
            this.withoutDueDateIssuesCount = withoutDueDateIssuesCount;
        }

        @Override
        public int compareTo(ProjectsReportData o) {
            int currentTotal = overdueIssuesCount
                    + withExpiringDueDateIssuesCount
                    + withoutDueDateIssuesCount;

            int otherTotal = o.overdueIssuesCount
                    + o.withExpiringDueDateIssuesCount
                    + o.withoutDueDateIssuesCount;

            return otherTotal - currentTotal;
        }
    }

    // TODO: 07.06.2019 data duplications!
    @Data
    public class EmployeesReportData implements Comparable<EmployeesReportData> {
        private int employeeId;
        private String employeeName;
        private int issueId;
        private String issueTitle;

        private int overdueIssuesCount;
        private int withExpiringDueDateIssuesCount;
        private int withoutDueDateIssuesCount;

        public EmployeesReportData(Employee employee, Issue issue) {
            employeeId = employee.getId();
            employeeName = employee.toString();
            issueId = issue.getId();
            issueTitle = issue.getTitle();
            overdueIssuesCount = issuesService.getOverdueIssuesByEmployeeCount(employee);
            withExpiringDueDateIssuesCount = issuesService.getIssuesWithExpiringDueDateByEmployeeCount(employee);
            withoutDueDateIssuesCount = issuesService.getIssuesWithoutDueDateByEmployeeCount(employee);
        }

        public EmployeesReportData(Employee employee, Issue issue,
                                   int overdueIssuesCount,
                                   int withExpiringDueDateIssuesCount,
                                   int withoutDueDateIssuesCount) {
            employeeId = employee.getId();
            employeeName = employee.toString();
            issueId = issue.getId();
            issueTitle = issue.getTitle();
            this.overdueIssuesCount = overdueIssuesCount;
            this.withExpiringDueDateIssuesCount = withExpiringDueDateIssuesCount;
            this.withoutDueDateIssuesCount = withoutDueDateIssuesCount;
        }

        @Override
        public int compareTo(EmployeesReportData o) {
            int currentTotal = overdueIssuesCount
                    + withExpiringDueDateIssuesCount
                    + withoutDueDateIssuesCount;

            int otherTotal = o.overdueIssuesCount
                    + o.withExpiringDueDateIssuesCount
                    + o.withoutDueDateIssuesCount;

            return otherTotal - currentTotal;
        }
    }

    private class ProjectsReportDataComparator implements Comparator<ProjectsReportData> {
        @Override
        public int compare(ProjectsReportData o1, ProjectsReportData o2) {
            return o1.compareTo(o2);
        }
    }

    private class EmployeesReportDataComparator implements Comparator<EmployeesReportData> {
        @Override
        public int compare(EmployeesReportData o1, EmployeesReportData o2) {
            return o1.compareTo(o2);
        }
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
