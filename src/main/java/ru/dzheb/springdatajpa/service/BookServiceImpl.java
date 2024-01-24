package ru.dzheb.springdatajpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dzheb.springdatajpa.model.Book;
import ru.dzheb.springdatajpa.repository.BookRepository;

import java.util.List;

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
    public String deleteBook(long id) {
    Book book = getBookById(id);
        if (book != null) {
            repository.deleteById(id);
            return "Книга id = " + id + " удалена";
        } else {
            return "Книга id = " + id + " не нрайдена";
        }

    }

    @Override
    public List<Book> allBooks() {
        return repository.findAll();
    }
}
