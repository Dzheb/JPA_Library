package ru.dzheb.springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dzheb.springdatajpa.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByLogin(String login);

}
