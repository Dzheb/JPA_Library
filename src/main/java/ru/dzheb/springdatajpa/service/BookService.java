package ru.dzheb.springdatajpa.service;

import org.springframework.stereotype.Service;
import ru.dzheb.springdatajpa.model.Book;

import java.util.List;

public interface BookService {
    Book getBookById(long id);

    Long addBook(String name);

    String deleteBook(long id);

    List<Book> allBooks();
}
