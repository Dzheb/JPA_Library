package ru.dzheb.springdatajpa.service;

import ru.dzheb.springdatajpa.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book getBookById(long id);

    Long addBook(String name);

    Optional<Long> deleteBook(long id);

    List<Book> allBooks();
}
