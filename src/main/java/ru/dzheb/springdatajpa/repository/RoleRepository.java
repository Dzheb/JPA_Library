package ru.dzheb.springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dzheb.springdatajpa.model.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {

//    Optional<Role> findByLogin(String role);

}
