package org.sergei.komarov.services;

import org.postgresql.util.PSQLException;
import org.sergei.komarov.models.IssuePriority;
import org.sergei.komarov.repositories.IssuePrioritiesRepository;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Pattern;

@Service
public class IssuePrioritiesService {

    private final IssuePrioritiesRepository issuePrioritiesRepository;

    public IssuePrioritiesService(IssuePrioritiesRepository issuePrioritiesRepository) {
        this.issuePrioritiesRepository = issuePrioritiesRepository;
    }

    public List<IssuePriority> getAll() {
        return issuePrioritiesRepository.findAll();
    }

    public void save(IssuePriority priority) {
        issuePrioritiesRepository.save(priority);
    }

    public void saveAll(Iterable<IssuePriority> priorities) {
        issuePrioritiesRepository.saveAll(priorities);
    }

    public IssuePriority getById(int id) {
        return issuePrioritiesRepository.findById(id).orElse(null);
    }

    public boolean isExistsById(int id) {
        return issuePrioritiesRepository.existsById(id);
    }

    public void deleteById(int id) {
        issuePrioritiesRepository.deleteById(id);
    }

    public void validateAndSave(Map<String, Object> attrs, String name) {
        if (name == null) {
            throw new NullPointerException();
        }

        String message = validate(name);
        if (message == null) {
            IssuePriority priority = new IssuePriority();
            priority.setName(name);
            message = trySave(priority);
        }

        addMessageToAttributes(attrs, message, "Успешно создан.");
    }

    public void validateAndUpdate(Map<String, Object> attrs, int id, String name) {
        if (name == null || attrs == null) {
            throw new NullPointerException();
        }

        String message;
        if (isExistsById(id)) {
            message = validate(name);

            IssuePriority priority = getById(id);
            if (message == null) {
                priority.setName(name);
                message = trySave(priority);
            } else {
                priority.setName(null);
            }
            attrs.put("entity", priority);
        } else {
            message = "Приоритет с таким ID не существует.";
        }

        addMessageToAttributes(attrs, message, "Успешно изменен.");
    }

    private void addMessageToAttributes(Map<String, Object> attrs, String error, String success) {
        if (attrs == null) {
            throw new NullPointerException();
        }

        if (error == null) {
            attrs.put("info", success);
        } else {
            attrs.put("error", error);
        }
    }

    //------------------ VALIDATIONS

    private static final int NAME_MAX_LENGTH = 20;
    private static final Pattern EXCLUDED_PATTERN = Pattern.compile("\\d");
    private static final String UNIQUE_VIOLATION = "23505";

    private String trySave(IssuePriority issuePriority) {
        String message = null;

        if (issuePriority == null) {
            throw new NullPointerException();
        }

        try {
            save(issuePriority);
        } catch (TransactionSystemException e) {
            PSQLException ex = (PSQLException) SQLExceptionParser.getUnwrappedPSQLException(e);
            if (!Objects.equals(ex, null) && Objects.equals(ex.getSQLState(), UNIQUE_VIOLATION)) {
                message = "Приоритет с таким названием уже существует.";
            }
        }

        return message;
    }

    private String validate(String name) {
        String message = null;

        if (name.isEmpty()) {
            message = "Название приоритета не должно быть пустым.";
        } else if (name.length() > NAME_MAX_LENGTH) {
            message = "Название приоритета не должно превышать "
                    + NAME_MAX_LENGTH
                    + " символов.";
        } else if (EXCLUDED_PATTERN.matcher(name).find()) {
            message = "Название приоритета не должно содержать цифры.";
        }

        return message;
    }
}
