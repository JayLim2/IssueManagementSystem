package org.sergei.komarov.services;

import lombok.NonNull;
import org.sergei.komarov.models.Employee;
import org.sergei.komarov.models.User;
import org.sergei.komarov.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class UsersService {
    private static final DateTimeFormatter USER_ID_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

    private final UsersRepository usersRepository;


    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void save(User user) {
        usersRepository.save(user);
    }

    public List<User> getAll() {
        return usersRepository.findAll();
    }

    public String getUserIdByEmployee(@NonNull Employee employee) {
        LocalDateTime now = LocalDateTime.now();

        return employee.getFirstName().toLowerCase().substring(0, 2) +
                employee.getLastName().toLowerCase().substring(0, 2) +
                now.format(USER_ID_DATETIME_FORMATTER);
    }

    public String getUserPassword() {
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

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        User user = new User();
        if (!username.equals("anonymousUser")) {
            user.setLogin(authentication.getName());
        } else {
            user.setLogin("Гость");
        }

        return user;
    }
}
