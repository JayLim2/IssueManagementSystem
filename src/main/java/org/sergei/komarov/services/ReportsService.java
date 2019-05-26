package org.sergei.komarov.services;

import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.sergei.komarov.models.EmployeePosition;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportsService {
    private final EmployeesService employeesService;
    private final EmployeePositionsService employeePositionsService;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("-yyyy-MM-dd-HH-mm-ss");
    private static final String REPORT_TEMPLATE = "/static/reports/Positions.jrxml";

    public String create() throws JRException, IOException {
        List<EmployeePosition> positions = employeePositionsService.getAll();
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(positions);

        File reportPattern = new File(getClass().getResource(REPORT_TEMPLATE).getPath());

        JasperDesign jasperDesign = JRXmlLoader.load(reportPattern);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                new HashMap<>(),
                beanColDataSource
        );

        String reportName = "report" + dateTimeFormatter.format(LocalDateTime.now()) + ".pdf";

        JasperExportManager.exportReportToPdfFile(
                jasperPrint,
                ".\\" + reportName
        );

        return reportName;
    }
    //--------------------------------
}
