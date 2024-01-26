package ru.dzheb.springdatajpa.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dzheb.springdatajpa.model.Book;
import ru.dzheb.springdatajpa.service.BookService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@Tag(name = "Book")
public class BookController {
    // dependency injection
    private final BookService bookservice;

    @GetMapping("/{id}")
    @Operation(summary = "get a book by id"
    ,description = "Поиск книги по идентификатору")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Ошибка клиента")
    })
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
    @Operation(summary = "add a book to the library"
            ,description = "Добавление книги в библиотеку")
    public Long addBook(@RequestBody Book book) {
        return bookservice.addBook(book.getName());

    }
    @DeleteMapping("/{id}")
    @Operation(summary = "delete a book by id"
            ,description = "Удаление книги по идентификатору")
    public String deleteBook(@PathVariable long id) {
        return bookservice.deleteBook(id);
    }

}
