package org.sergei.komarov.services;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.sergei.komarov.models.Employee;
import org.sergei.komarov.models.User;
import org.sergei.komarov.models.UserRole;
import org.sergei.komarov.repositories.UsersRepository;
import org.sergei.komarov.utils.Validators;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@AllArgsConstructor
public class UsersService implements JpaService<User, String> {
    private static final DateTimeFormatter USER_ID_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveAndGet(User user) {
        return usersRepository.save(user);
    }

    @Override
    public void save(User user) {
        usersRepository.save(user);
    }

    @Override
    public void saveAll(Iterable<User> iterable) {
        usersRepository.saveAll(iterable);
    }

    @Override
    public void delete(User entity) {
        usersRepository.delete(entity);
    }

    @Override
    public void deleteById(String id) {
        usersRepository.deleteById(id);
    }

    public List<User> getAll() {
        return usersRepository.findAll();
    }

    @Override
    public User getById(String id) {
        return usersRepository.getOne(id);
    }

    @Override
    public boolean isExistsById(String id) {
        return usersRepository.existsById(id);
    }

    public User createUserByEmployee(@NonNull Employee employee) {
        String login = getUserIdByEmployee(employee);
        String password = getUserPassword();
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(UserRole.EMPLOYEE);
        return user;
    }

    private String getUserIdByEmployee(@NonNull Employee employee) {
        LocalDateTime now = LocalDateTime.now();

        return employee.getFirstName().toLowerCase().substring(0, 2) +
                employee.getLastName().toLowerCase().substring(0, 2) +
                now.format(USER_ID_DATETIME_FORMATTER);
    }

    private String getUserPassword() {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 14; i++) {
            if (i < 8) {
                char symbol = (char) (random.nextInt(26) + (random.nextBoolean() ? 65 : 97));
                builder.append(symbol);
            } else if (i == 8) {
                builder.append('.');
            } else {
                builder.append(random.nextInt(10));
            }
        }
        return builder.toString();
    }

    // FIXME: 22.05.2019 NULLABLE
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        User user = new User();
        if (!username.equals("anonymousUser")) {
            user = usersRepository.findByLogin(username);
        } else {
            user.setLogin("Гость");
        }

        return user;
    }

    public void validateAndUpdate(Map<String, Object> attrs,
                                  String id, String newPassword, UserRole userRole) {
        if (id == null || attrs == null) {
            throw new NullPointerException();
        }

        String message;
        if (isExistsById(id)) {
            message = Validators.validateUserData(newPassword);

            User user = getById(id);
            if (message == null) {
                if (!newPassword.trim().isEmpty()) {
                    user.setPassword(bCryptPasswordEncoder.encode(newPassword));
                }
                user.setRole(userRole);
                message = trySave(user);
            } else {
                user.setPassword(null);
            }
            attrs.put("entity", user);
        } else {
            message = "Пользователь с таким ID не существует.";
        }

        addMessageToAttributes(attrs, message, "Данные о пользователе успешно изменены.");
    }
}
