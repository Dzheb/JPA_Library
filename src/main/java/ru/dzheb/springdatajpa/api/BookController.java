package ru.dzheb.springdatajpa.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dzheb.springdatajpa.model.Book;
import ru.dzheb.springdatajpa.service.BookService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    // dependency injection
    private final BookService bookservice;

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookName(@PathVariable long id) {
       final Book book;
        book = bookservice.getBookById(id);
        if (book == null) {
            System.out.println("Книга: не найдена");
            return ResponseEntity.notFound().build();
        } else {
            System.out.println("Книга: " + bookservice.getBookById(id));
            return ResponseEntity.status(HttpStatus.OK).body(book);

        }
    }

    @PostMapping
    public Long addBook(@RequestBody Book book) {
        return bookservice.addBook(book.getName());

    }
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable long id) {
        return bookservice.deleteBook(id);
    }

}
