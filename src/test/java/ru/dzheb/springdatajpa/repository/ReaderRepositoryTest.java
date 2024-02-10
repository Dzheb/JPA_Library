package ru.dzheb.springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dzheb.springdatajpa.model.Book;
import ru.dzheb.springdatajpa.model.Reader;

public interface ReaderRepositoryTest extends
        JpaRepository<Reader,Long>{}
