package org.sergei.komarov.services;

import org.postgresql.util.PSQLException;
import org.sergei.komarov.utils.SQLExceptionParser;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface JpaService<E, I> {
    //fields
    String UNIQUE_VIOLATION_CODE = "23505";

    //methods
    List<E> getAll();

    E getById(I id);

    boolean isExistsById(I id);

    void save(E entity);

    void saveAll(Iterable<E> iterable);

    void delete(E entity);

    void deleteById(I id);

    default String trySave(E entity) {
        String message = null;

        if (entity == null) {
            throw new NullPointerException();
        }

        try {
            save(entity);
        } catch (TransactionSystemException e) {
            PSQLException ex = (PSQLException) SQLExceptionParser.getUnwrappedPSQLException(e);
            if (!Objects.equals(ex, null) && Objects.equals(ex.getSQLState(), UNIQUE_VIOLATION_CODE)) {
                message = "Объект с таким названием уже существует.";
            }
        }

        return message;
    }

    default void addMessageToAttributes(Map<String, Object> modelAttributes, String error, String success) {
        if (modelAttributes == null) {
            throw new NullPointerException();
        }

        if (error == null) {
            modelAttributes.put("info", success);
        } else {
            modelAttributes.put("error", error);
        }
    }
}
