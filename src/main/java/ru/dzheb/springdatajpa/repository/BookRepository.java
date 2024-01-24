package ru.dzheb.springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dzheb.springdatajpa.model.Book;

public interface BookRepository extends
        JpaRepository<Book,Long>{}
