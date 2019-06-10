package org.sergei.komarov.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.sergei.komarov.models.Employee;
import org.sergei.komarov.models.Issue;
import org.sergei.komarov.models.Project;
import org.sergei.komarov.utils.Comparators;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportsService {
    private final EmployeesService employeesService;
    private final ProjectsService projectsService;
    private final IssuesService issuesService;

    private final Comparators comparators;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("-yyyy-MM-dd-HH-mm-ss");
    private static final String PROJECTS_REPORT_TEMPLATE = "/static/reports/Projects.jrxml";
    private static final String EMPLOYEES_REPORT_TEMPLATE = "/static/reports/Employees.jrxml";

    public String createProjectsReport() throws JRException, IOException {
        //Data
        List<Project> projects = projectsService.getAll();
        List<ProjectsReportData> projectsReportDataList = new ArrayList<>();
        projects.sort(comparators.getProjectsComparator());
        for (Project project : projects) {
            List<Issue> overdueIssuesByProject = issuesService.getOverdueIssuesByProject(project);
            List<Issue> withExpiringDueDateIssuesByProject = issuesService.getIssuesWithExpiringDueDateByProject(project);
            List<Issue> withoutDueDateIssuesByProject = issuesService.getIssuesWithoutDueDateByProject(project);

            int overdue = overdueIssuesByProject.size();
            int expiring = withExpiringDueDateIssuesByProject.size();
            int withoutDueDate = withoutDueDateIssuesByProject.size();

            List<Issue> issues = new ArrayList<>(overdue + expiring + withoutDueDate);
            issues.addAll(overdueIssuesByProject);
            issues.addAll(withExpiringDueDateIssuesByProject);
            issues.addAll(withoutDueDateIssuesByProject);

            for (Issue issue : issues) {
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
        employees.sort(comparators.getEmployeesComparator());
        for (Employee employee : employees) {
            List<Issue> overdueIssuesByEmployee = issuesService.getOverdueIssuesByEmployee(employee);
            List<Issue> withExpiringDueDateIssuesByEmployee = issuesService.getIssuesWithExpiringDueDateByEmployee(employee);
            List<Issue> withoutDueDateIssuesByEmployee = issuesService.getIssuesWithoutDueDateByEmployee(employee);

            int overdue = overdueIssuesByEmployee.size();
            int expiring = withExpiringDueDateIssuesByEmployee.size();
            int withoutDueDate = withoutDueDateIssuesByEmployee.size();

            List<Issue> issues = new ArrayList<>(overdue + expiring + withoutDueDate);
            issues.addAll(overdueIssuesByEmployee);
            issues.addAll(withExpiringDueDateIssuesByEmployee);
            issues.addAll(withoutDueDateIssuesByEmployee);

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

        String reportName = prefix + "report" + DATE_TIME_FORMATTER.format(LocalDateTime.now()) + ".pdf";

        JasperExportManager.exportReportToPdfFile(
                jasperPrint,
                ".\\" + reportName
        );

        return reportName;
    }

    // TODO: 07.06.2019 data duplications!
    @Data
    public class ProjectsReportData {
        private int projectId;
        private String projectTitle;
        private int issueId;
        private String issueTitle;
        private String issueAssigneeName;
        private String issueDueDate;

        private int overdueIssuesCount;
        private int withExpiringDueDateIssuesCount;
        private int withoutDueDateIssuesCount;

        public ProjectsReportData(Project project, Issue issue) {
            projectId = project.getId();
            projectTitle = project.getTitle();
            issueId = issue.getId();
            issueTitle = issue.getTitle();
            issueAssigneeName = issue.getAssignee() != null ? issue.getAssignee().toString() : "не назначен";
            issueDueDate = issue.getDueDate() != null ? DateTimeFormatter.ofPattern("dd MMMM yyyy").format(issue.getDueDate()) : "не назначен";
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
            issueAssigneeName = issue.getAssignee() != null ? issue.getAssignee().toString() : "не назначен";
            issueDueDate = issue.getDueDate() != null ? DateTimeFormatter.ofPattern("dd MMMM yyyy").format(issue.getDueDate()) : "не назначен";
            this.overdueIssuesCount = overdueIssuesCount;
            this.withExpiringDueDateIssuesCount = withExpiringDueDateIssuesCount;
            this.withoutDueDateIssuesCount = withoutDueDateIssuesCount;
        }
    }

    // TODO: 07.06.2019 data duplications!
    @Data
    public class EmployeesReportData {
        private int employeeId;
        private String employeeName;
        private int issueId;
        private String issueTitle;
        private String issueAssigneeName;
        private String issueDueDate;

        private int overdueIssuesCount;
        private int withExpiringDueDateIssuesCount;
        private int withoutDueDateIssuesCount;

        public EmployeesReportData(Employee employee, Issue issue) {
            employeeId = employee.getId();
            employeeName = employee.toString();
            issueId = issue.getId();
            issueTitle = issue.getTitle();
            issueAssigneeName = issue.getAssignee() != null ? issue.getAssignee().toString() : "не назначен";
            issueDueDate = issue.getDueDate() != null ? DateTimeFormatter.ofPattern("dd MMMM yyyy").format(issue.getDueDate()) : "не назначен";
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
            issueAssigneeName = issue.getAssignee() != null ? issue.getAssignee().toString() : "не назначен";
            issueDueDate = issue.getDueDate() != null ? DateTimeFormatter.ofPattern("dd MMMM yyyy").format(issue.getDueDate()) : "не назначен";
            this.overdueIssuesCount = overdueIssuesCount;
            this.withExpiringDueDateIssuesCount = withExpiringDueDateIssuesCount;
            this.withoutDueDateIssuesCount = withoutDueDateIssuesCount;
        }
    }
}
