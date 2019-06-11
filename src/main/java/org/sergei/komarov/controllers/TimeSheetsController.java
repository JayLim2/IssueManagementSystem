package org.sergei.komarov.controllers;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.Employee;
import org.sergei.komarov.models.Issue;
import org.sergei.komarov.models.TimeSheet;
import org.sergei.komarov.models.keys.TimeSheetKey;
import org.sergei.komarov.services.IssuesService;
import org.sergei.komarov.services.TimeSheetsService;
import org.sergei.komarov.services.UsersService;
import org.sergei.komarov.utils.Handlers;
import org.sergei.komarov.utils.Validators;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.sergei.komarov.utils.Handlers.tryParseFloat;
import static org.sergei.komarov.utils.Handlers.tryParseInt;

@RestController
@RequestMapping("/timeSheets")
@AllArgsConstructor
public class TimeSheetsController {
    private final UsersService usersService;
    private final IssuesService issuesService;
    private final TimeSheetsService timeSheetsService;

    // TODO: 07.06.2019 grouping

    @PostMapping("/add")
    public Map<String, Object> addTimeSheet(String taskId,
                                            String day1, String day2, String day3,
                                            String day4, String day5, String day6,
                                            String day7,
                                            String comment) {

        Map<String, Object> attrs = new HashMap<>();

        int taskIdValue = tryParseInt(taskId);
        float[] hours = new float[]{
                tryParseFloat(day1), tryParseFloat(day2), tryParseFloat(day3),
                tryParseFloat(day4), tryParseFloat(day5), tryParseFloat(day6),
                tryParseFloat(day7)
        };
        String validationMessage = Validators.validateWeekValues(hours);
        if (validationMessage != null) {
            attrs.put("error", validationMessage);
            return attrs;
        }

        Employee employee = usersService.getCurrentUser().getEmployee();
        Issue issue = issuesService.getById(taskIdValue);

        TimeSheet timeSheet = new TimeSheet();
        timeSheet.setStartDate(Handlers.getCurrentWeek());
        if (employee == null) {
            validationMessage = "Такой сотрудник не существует.";
        }
        timeSheet.setEmployee(employee);
        timeSheet.setIssue(issue);
        if (issue == null) {
            validationMessage = "Задача с таким ID не существует.";
        }
        timeSheet.setWeekValues(hours);
        if (comment != null) {
            comment = comment.trim();
        }
        timeSheet.setComment(comment);
        timeSheetsService.save(timeSheet);

        attrs.put("info", "Отчет об использовании рабочего времени сохранен.");

        return attrs;
    }

    @PostMapping("/delete")
    public Map<String, Object> deleteTimeSheet(int employeeId, int taskId, String startDate) {
        Map<String, Object> attrs = new HashMap<>();

        try {
            TimeSheetKey key = new TimeSheetKey();
            key.setEmployee(employeeId);
            key.setIssue(taskId);
            key.setStartDate(startDate);
            timeSheetsService.deleteById(key);
            attrs.put("info", "Информация об отработанных часах удалена.");
        } catch (Exception e) {
            e.printStackTrace();
            attrs.put("error", "Ошибка удаления информации об отработанных часах.");
        }

        return attrs;
    }

}
