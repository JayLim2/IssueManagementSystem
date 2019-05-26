package org.sergei.komarov.models;

public enum ServiceComment {
    OPENED,
    CLOSED,
    REOPENED,
    CHANGED_TITLE,
    CHANGED_DESCRIPTION,
    CHANGED_PROJECT,
    CHANGED_PROJECT_VERSION,
    CHANGED_WORKFLOW_STATUS,
    CHANGED_TYPE,
    CHANGED_PRIORITY,
    CHANGED_ASSIGNEE,
    CHANGED_PARENT_ISSUE,
    CHANGED_DUE_DATE,
    POSTED_COMMENT;

    @Override
    public String toString() {
        String converted = "";

        switch (this) {
            case OPENED:
                converted = "открыл задачу";
                break;
            case CLOSED:
                converted = "закрыл задачу";
                break;
            case REOPENED:
                converted = "переоткрыл задачу";
                break;
            case CHANGED_TITLE:
                converted = "изменил название";
                break;
            case CHANGED_DESCRIPTION:
                converted = "изменил описание";
                break;
            case CHANGED_PROJECT:
                converted = "изменил проект";
                break;
            case CHANGED_PROJECT_VERSION:
                converted = "изменил версию проекта";
                break;
            case CHANGED_WORKFLOW_STATUS:
                converted = "изменил статус";
                break;
            case CHANGED_TYPE:
                converted = "изменил тип";
                break;
            case CHANGED_PRIORITY:
                converted = "изменил приоритет";
                break;
            case CHANGED_ASSIGNEE:
                converted = "назначил ответственным";
                break;
            case CHANGED_PARENT_ISSUE:
                converted = "изменил родительскую задачу";
                break;
            case CHANGED_DUE_DATE:
                converted = "изменил дату окончания выполнения";
                break;
            case POSTED_COMMENT:
                converted = "опубликовал комментарий";
        }

        return converted;
    }
}