package org.sergei.komarov.utils;

import org.postgresql.util.PSQLException;
import org.springframework.transaction.TransactionSystemException;

public class SQLExceptionParser {

    public static String getReadableMessage(Throwable e) {
        StringBuilder builder = new StringBuilder();

        if (e instanceof PSQLException) {
            String message = e.getMessage();
            if (message.contains("_check")) {
                builder.append("Нарушение ограничений на ввод.");
            }
            if (message.contains("_key")) {
                builder.append("Нарушение уникальности.");
            }
        } else {
            builder.append("Null or no SQL exception.");
        }

        return builder.toString();
    }

    public static Throwable getUnwrappedPSQLException(TransactionSystemException e) {
        Throwable result = null;

        if (e != null) {
            result = e.getCause();
            while (result != null && !(result instanceof PSQLException)) {
                result = result.getCause();
            }
        }

        return result;
    }
}
