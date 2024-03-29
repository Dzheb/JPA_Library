package ru.dzheb.springdatajpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dzheb.springdatajpa.model.Book;
import ru.dzheb.springdatajpa.repository.BookRepository;

import java.util.List;
import java.util.Optional;


@Service
public class BookServiceImpl implements BookService{
    private final BookRepository repository;
    @Autowired
    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book getBookById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Long addBook(String name) {
        Book book = new Book(name);
        repository.save(book);
        return book.getId();
    }

    @Override
    public Optional<Long> deleteBook(long id) {
        Book book = getBookById(id);
        if (book != null) {
            repository.deleteById(id);
            return Optional.of(id);
        } else {
            return Optional.empty();
        }

    }

    @Override
    public List<Book> allBooks() {
        return repository.findAll();
    }
}