package org.sergei.komarov.utils;

import org.sergei.komarov.models.IssueWorkflowStatus;

import java.util.List;
import java.util.regex.Pattern;

public class Validators {

    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 20;

    public static boolean isArrayWithNulls(Object... data) {
        if (data == null) {
            return true;
        }

        for (int i = 0; i < data.length && data[i] != null; i++) ;

        return false;
    }

    private static final int NAME_MAX_LENGTH = 20;
    private static final Pattern EXCLUDED_PATTERN = Pattern.compile("\\d");

    public static String validateIssuePriorityData(String name) {
        String message = null;

        if (name.isEmpty()) {
            message = "Название приоритета задач не должно быть пустым.";
        } else if (name.length() > NAME_MAX_LENGTH) {
            message = "Название приоритета задач не должно превышать "
                    + NAME_MAX_LENGTH
                    + " символов.";
        } else if (EXCLUDED_PATTERN.matcher(name).find()) {
            message = "Название приоритета задач не должно содержать цифры.";
        }

        return message;
    }

    public static String validateIssueTypeData(String name, List<IssueWorkflowStatus> statuses) {
        String message = null;

        if (name.isEmpty()) {
            message = "Название типа задач не должно быть пустым.";
        } else if (name.length() > NAME_MAX_LENGTH) {
            message = "Название типа задач не должно превышать "
                    + NAME_MAX_LENGTH
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

        if (name.isEmpty()) {
            message = "Название статуса рабочего процесса не должно быть пустым.";
        } else if (name.length() > NAME_MAX_LENGTH) {
            message = "Название статуса рабочего процесса не должно превышать "
                    + NAME_MAX_LENGTH
                    + " символов.";
        } else if (EXCLUDED_PATTERN.matcher(name).find()) {
            message = "Название статуса рабочего процесса не должно содержать цифры.";
        }

        return message;
    }

    public static String validateUserRoleData(String name) {
        String message = null;

        if (name.isEmpty()) {
            message = "Название роли пользователя не должно быть пустым.";
        } else if (name.length() > NAME_MAX_LENGTH) {
            message = "Название роли пользователя не должно превышать "
                    + NAME_MAX_LENGTH
                    + " символов.";
        } else if (EXCLUDED_PATTERN.matcher(name).find()) {
            message = "Название роли пользователя не должно содержать цифры.";
        }

        return message;
    }

    public static String validateProjectRoleData(String name) {
        String message = null;

        if (name.isEmpty()) {
            message = "Название проектной роли не должно быть пустым.";
        } else if (name.length() > NAME_MAX_LENGTH) {
            message = "Название проектной роли не должно превышать "
                    + NAME_MAX_LENGTH
                    + " символов.";
        } else if (EXCLUDED_PATTERN.matcher(name).find()) {
            message = "Название проектной роли не должно содержать цифры.";
        }

        return message;
    }

    public static String validateProjectTypeData(String name) {
        String message = null;

        if (name.isEmpty()) {
            message = "Название типа проекта не должно быть пустым.";
        } else if (name.length() > NAME_MAX_LENGTH) {
            message = "Название типа проекта не должно превышать "
                    + NAME_MAX_LENGTH
                    + " символов.";
        } else if (EXCLUDED_PATTERN.matcher(name).find()) {
            message = "Название типа проекта не должно содержать цифры.";
        }

        return message;
    }

    public static String validateEmployeePositionData(String name) {
        String message = null;

        if (name.isEmpty()) {
            message = "Название должности не должно быть пустым.";
        } else if (name.length() > NAME_MAX_LENGTH) {
            message = "Название должности не должно превышать "
                    + NAME_MAX_LENGTH
                    + " символов.";
        } else if (EXCLUDED_PATTERN.matcher(name).find()) {
            message = "Название должности не должно содержать цифры.";
        }

        return message;
    }
}
