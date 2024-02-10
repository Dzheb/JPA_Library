package ru.dzheb.springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dzheb.springdatajpa.model.Book;

public interface BookRepositoryTest extends
        JpaRepository<Book,Long>{}
