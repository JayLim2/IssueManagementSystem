package org.sergei.komarov.controllers;

import lombok.AllArgsConstructor;
import org.sergei.komarov.services.ReportsService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reports")
@AllArgsConstructor
public class ReportsController {

    private final ReportsService reportsService;

    @PostMapping("/build")
    public Map<String, Object> buildReport(int reportType) {
        Map<String, Object> attrs = new HashMap<>();

        try {
            String reportName = null;

            switch (reportType) {
                case 1:
                    reportName = reportsService.createProjectsReport();
                    break;
                case 2:
                    reportName = reportsService.createEmployeesReport();
                    break;
            }

            String link = "/reports/download/" + reportName;
            attrs.put("info", "Отчет успешно построен.");
            attrs.put("link", link);
        } catch (Exception e) {
            e.printStackTrace();
            attrs.put("error", "Ошибка при построении отчета.");
        }

        return attrs;
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<InputStreamResource> download(@PathVariable String fileName) throws IOException {

        MediaType mediaType = MediaType.APPLICATION_PDF;

        File file = new File("./" + fileName);
        System.out.println(file.getAbsolutePath());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName()
                )
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }
}
