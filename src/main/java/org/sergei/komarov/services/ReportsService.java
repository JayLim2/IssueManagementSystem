package org.sergei.komarov.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.sergei.komarov.models.Employee;
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
        for (Project project : projects) {
            projectsReportDataList.add(new ProjectsReportData(project));
        }
        projectsReportDataList.sort(new ProjectsReportDataComparator());

        //Reporting
        return buildReport(projectsReportDataList, PROJECTS_REPORT_TEMPLATE);
    }

    public String createEmployeesReport() throws JRException, IOException {
        //Data
        List<Employee> employees = employeesService.getAll();
        List<EmployeesReportData> employeesReportDataList = new ArrayList<>();
        for (Employee employee : employees) {
            employeesReportDataList.add(new EmployeesReportData(employee));
        }
        employeesReportDataList.sort(new EmployeesReportDataComparator().reversed());

        //Reporting
        return buildReport(employeesReportDataList, EMPLOYEES_REPORT_TEMPLATE);
    }

    private <E> String buildReport(List<E> list, String reportPattern) throws JRException {
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);

        File reportPatternFile = new File(getClass().getResource(reportPattern).getPath());

        JasperDesign jasperDesign = JRXmlLoader.load(reportPatternFile);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                new HashMap<>(),
                beanColDataSource
        );

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

    @Data
    public class ProjectsReportData implements Comparable<ProjectsReportData> {
        private int projectId;
        private String projectTitle;
        private int overdueIssuesCount;
        private int withExpiringDueDateIssuesCount;
        private int withoutDueDateIssuesCount;

        public ProjectsReportData(Project project) {
            projectId = project.getId();
            projectTitle = project.getTitle();
            overdueIssuesCount = issuesService.getOverdueIssuesByProjectCount(project);
            withExpiringDueDateIssuesCount = issuesService.getIssuesWithExpiringDueDateByProjectCount(project);
            withoutDueDateIssuesCount = issuesService.getIssuesWithoutDueDateByProjectCount(project);
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

    @Data
    public class EmployeesReportData implements Comparable<EmployeesReportData> {
        private int employeeId;
        private String employeeName;
        private int overdueIssuesCount;
        private int withExpiringDueDateIssuesCount;
        private int withoutDueDateIssuesCount;

        public EmployeesReportData(Employee employee) {
            employeeId = employee.getId();
            employeeName = employee.toString();
            overdueIssuesCount = issuesService.getOverdueIssuesByEmployeeCount(employee);
            withExpiringDueDateIssuesCount = issuesService.getIssuesWithExpiringDueDateByEmployeeCount(employee);
            withoutDueDateIssuesCount = issuesService.getIssuesWithoutDueDateByEmployeeCount(employee);
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

            int total1 = o1.overdueIssuesCount
                    + o1.withExpiringDueDateIssuesCount
                    + o1.withoutDueDateIssuesCount;

            int total2 = o2.overdueIssuesCount
                    + o2.withExpiringDueDateIssuesCount
                    + o2.withoutDueDateIssuesCount;

            return total2 - total1;
        }

    }

    private class EmployeesReportDataComparator implements Comparator<EmployeesReportData> {

        @Override
        public int compare(EmployeesReportData o1, EmployeesReportData o2) {

            int total1 = o1.overdueIssuesCount
                    + o1.withExpiringDueDateIssuesCount
                    + o1.withoutDueDateIssuesCount;

            int total2 = o2.overdueIssuesCount
                    + o2.withExpiringDueDateIssuesCount
                    + o2.withoutDueDateIssuesCount;

            return total2 - total1;
        }

    }
}
