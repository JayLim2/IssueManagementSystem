package org.sergei.komarov.utils;

import org.sergei.komarov.models.EmployeePosition;
import org.sergei.komarov.models.IssueWorkflowStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {

    private static final Pattern EXCLUDED_PATTERN = Pattern.compile("\\d");

    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 20;

    private static final int MAX_ISSUE_PRIORITY_NAME_LENGTH = 40;
    private static final int MAX_ISSUE_TYPE_NAME_LENGTH = 40;
    private static final int MAX_ISSUE_WORKFLOW_STATUS_NAME_LENGTH = 60;
    private static final int MAX_PROJECT_ROLE_NAME_LENGTH = 100;
    private static final int MAX_PROJECT_TYPE_NAME_LENGTH = 100;
    private static final int MAX_EMPLOYEE_POSITION_NAME_LENGTH = 100;
    private static final int MAX_EMPLOYEE_NAME_LENGTH = 150;
    private static final int MAX_PROJECT_TITLE_LENGTH = 300;
    private static final int MAX_ISSUE_TITLE_LENGTH = 300;
    private static final int MAX_ISSUE_DESCRIPTION_LENGTH = 2500;

    public static boolean isArrayWithNulls(Object... data) {
        if (data == null) {
            return true;
        }

        for (int i = 0; i < data.length && data[i] != null; i++) ;

        return false;
    }

    public static String validateIssuePriorityData(String name) {
        String message = null;

        name = name.trim();

        if (name.isEmpty()) {
            message = "Название приоритета задач не должно быть пустым.";
        } else if (name.length() > MAX_ISSUE_PRIORITY_NAME_LENGTH) {
            message = "Название приоритета задач не должно превышать "
                    + MAX_ISSUE_PRIORITY_NAME_LENGTH
                    + " символов.";
        } else if (EXCLUDED_PATTERN.matcher(name).find()) {
            message = "Название приоритета задач не должно содержать цифры.";
        }

        return message;
    }

    public static String validateIssueTypeData(String name, List<IssueWorkflowStatus> statuses) {
        String message = null;

        name = name.trim();

        if (name.isEmpty()) {
            message = "Название типа задач не должно быть пустым.";
        } else if (name.length() > MAX_ISSUE_TYPE_NAME_LENGTH) {
            message = "Название типа задач не должно превышать "
                    + MAX_ISSUE_TYPE_NAME_LENGTH
                    + " символов.";
        } else if (EXCLUDED_PATTERN.matcher(name).find()) {
            message = "Название типа задач не должно содержать цифры.";
        } else if (statuses.size() == 0) {
            message = "Типу задач должен соответствовать хотя бы 1 статус рабочего процесса.";
        }

        return message;
    }

    public static String validateWorkflowStatusData(String name) {
        String message = null;

        name = name.trim();

        if (name.isEmpty()) {
            message = "Название статуса рабочего процесса не должно быть пустым.";
        } else if (name.length() > MAX_ISSUE_WORKFLOW_STATUS_NAME_LENGTH) {
            message = "Название статуса рабочего процесса не должно превышать "
                    + MAX_ISSUE_WORKFLOW_STATUS_NAME_LENGTH
                    + " символов.";
        } else if (EXCLUDED_PATTERN.matcher(name).find()) {
            message = "Название статуса рабочего процесса не должно содержать цифры.";
        }

        return message;
    }

    @Deprecated
    public static String validateUserRoleData(String name) {
        String message = null;

        name = name.trim();

        if (name.isEmpty()) {
            message = "Название роли пользователя не должно быть пустым.";
        } else if (name.length() > 20) {
            message = "Название роли пользователя не должно превышать "
                    + 20
                    + " символов.";
        } else if (EXCLUDED_PATTERN.matcher(name).find()) {
            message = "Название роли пользователя не должно содержать цифры.";
        }

        return message;
    }

    public static String validateProjectRoleData(String name) {
        String message = null;

        name = name.trim();

        if (name.isEmpty()) {
            message = "Название проектной роли не должно быть пустым.";
        } else if (name.length() > MAX_PROJECT_ROLE_NAME_LENGTH) {
            message = "Название проектной роли не должно превышать "
                    + MAX_PROJECT_ROLE_NAME_LENGTH
                    + " символов.";
        } else if (EXCLUDED_PATTERN.matcher(name).find()) {
            message = "Название проектной роли не должно содержать цифры.";
        }

        return message;
    }

    public static String validateProjectTypeData(String name) {
        String message = null;

        name = name.trim();

        if (name.isEmpty()) {
            message = "Название типа проекта не должно быть пустым.";
        } else if (name.length() > MAX_PROJECT_TYPE_NAME_LENGTH) {
            message = "Название типа проекта не должно превышать "
                    + MAX_PROJECT_TYPE_NAME_LENGTH
                    + " символов.";
        } else if (EXCLUDED_PATTERN.matcher(name).find()) {
            message = "Название типа проекта не должно содержать цифры.";
        }

        return message;
    }

    public static String validateEmployeePositionData(String name) {
        String message = null;

        name = name.trim();

        if (name.isEmpty()) {
            message = "Название должности не должно быть пустым.";
        } else if (name.length() > MAX_EMPLOYEE_POSITION_NAME_LENGTH) {
            message = "Название должности не должно превышать "
                    + MAX_EMPLOYEE_POSITION_NAME_LENGTH
                    + " символов.";
        } else if (EXCLUDED_PATTERN.matcher(name).find()) {
            message = "Название должности не должно содержать цифры.";
        }

        return message;
    }

    public static String validateEmployeeData(String firstName, String middleName, String lastName,
                                              EmployeePosition employeePosition) {
        String message = null;

        firstName = firstName.trim();
        lastName = lastName.trim();
        middleName = Optional.ofNullable(middleName).orElse("").trim();

        if (firstName.trim().isEmpty()) {
            message = "Имя сотрудника не должно быть пустым.";
        } else if (firstName.length() > MAX_EMPLOYEE_NAME_LENGTH) {
            message = "Имя сотрудника не должно превышать "
                    + MAX_EMPLOYEE_NAME_LENGTH
                    + " символов.";
        } else if (lastName.isEmpty()) {
            message = "Фамилия сотрудника не должно превышать "
                    + MAX_EMPLOYEE_NAME_LENGTH
                    + " символов.";
        } else if (lastName.length() > MAX_EMPLOYEE_NAME_LENGTH) {
            message = "Фамилия сотрудника не должно превышать "
                    + MAX_EMPLOYEE_NAME_LENGTH
                    + " символов.";
        } else if (EXCLUDED_PATTERN.matcher(firstName).find()) {
            message = "Имя сотрудника не должно содержать цифры.";
        } else if (EXCLUDED_PATTERN.matcher(lastName).find()) {
            message = "Фамилия сотрудника не должна содержать цифры.";
        } else if (EXCLUDED_PATTERN.matcher(firstName).find()) {
            message = "Отчество сотрудника не должно содержать цифры.";
        }

        return message;
    }

    public static String validateUserData(String newPassword) {
        String message = null;

        Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9-_\\.]{" + MIN_PASSWORD_LENGTH + "," + MAX_PASSWORD_LENGTH + "}$");
        Matcher matcher = pattern.matcher(newPassword);

        boolean isEmpty = newPassword.isEmpty();

        if (!isEmpty && newPassword.length() > MAX_PASSWORD_LENGTH) {
            message = "Длина пароля не должна превышать " + MAX_PASSWORD_LENGTH + " символов.";
        } else if (!isEmpty && newPassword.length() < MIN_PASSWORD_LENGTH) {
            message = "Длина пароля не должна быть меньше " + MIN_PASSWORD_LENGTH + " символов.";
        } else if (!isEmpty && !matcher.find()) {
            message = "Пароль должен начинаться с буквы, состоять из букв, цифр, символа _ или точки.";
        } else if (!isEmpty && newPassword.toLowerCase().equals(newPassword)) {
            message = "Хотя бы 1 буква должна быть в верхнем регистре.";
        }

        return message;
    }

    public static String validateProjectData(String title) {
        String message = null;

        title = title.trim();

        if (title.isEmpty()) {
            message = "Название проекта не должно быть пустым.";
        } else if (title.length() > MAX_PROJECT_TITLE_LENGTH) {
            message = "Название проекта не должно превышать "
                    + MAX_PROJECT_TITLE_LENGTH
                    + " символов.";
        }

        return message;
    }

    public static String validateIssueData(String title, String description,
                                           LocalDate dueDate) {
        String message = null;

        title = title.trim();
        description = description.trim();

        if (title.isEmpty()) {
            message = "Название задачи не должно быть пустым.";
        } else if (title.length() > MAX_ISSUE_TITLE_LENGTH) {
            message = "Название задачи не должно превышать "
                    + MAX_ISSUE_TITLE_LENGTH
                    + " символов.";
        } else if (EXCLUDED_PATTERN.matcher(title).find()) {
            message = "Название проекта не должно содержать цифры.";
        } else if (description.isEmpty()) {
            message = "Задача обязательно должна иметь описание.";
        } else if (dueDate != null && dueDate.isBefore(LocalDate.now())) {
            message = "Дата окончания выполнения задачи должна быть не раньше текущего дня.";
        }

        return message;
    }
}
