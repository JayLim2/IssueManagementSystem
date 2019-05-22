package org.sergei.komarov.repositories;

import org.sergei.komarov.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, String> {
    User findByLogin(String login);
}
