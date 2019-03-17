package org.sergei.komarov.services;

import org.sergei.komarov.models.User;
import org.sergei.komarov.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
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
}
