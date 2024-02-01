package ru.dzheb.springdatajpa.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dzheb.springdatajpa.model.Issue;
import ru.dzheb.springdatajpa.model.Reader;
import ru.dzheb.springdatajpa.service.IssuerService;
import ru.dzheb.springdatajpa.service.ReaderService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reader")
@Tag(name = "Reader")
public class ReaderController {
    // dependency injection
    private final ReaderService readerService;
    private final IssuerService issuerService;


    @GetMapping("/{id}")
    @Operation(summary = "get a reader by id"
            ,description = "Поиск читателя по идентификатору")
    public ResponseEntity<Reader> getBookName(@PathVariable long id) {
        //log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());
        final Reader reader;
        reader = readerService.getReaderById(id);
        if (reader == null) {
            System.out.println("Читатель: не найден");
            return ResponseEntity.notFound().build();
        } else {
            System.out.println("Читатель: " + readerService.getReaderById(id));
            return ResponseEntity.status(HttpStatus.OK).body(reader);

        }
    }

    @PostMapping
    @Operation(summary = "add a reader to the library"
            ,description = "Добавление читателя в библиотеку")
    public Long addReader(@RequestBody Reader reader) {
        return readerService.addReader(reader.getName());

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete reader by id"
            ,description = "Удаление читателя по идентификатору")
    public String deleteReader(@PathVariable long id) {
        return readerService.deleteReader(id);
    }

    //
    @GetMapping("/{id}/issue")
    @Operation(summary = "get books issued to the reader by Id"
            ,description = "Получение книг выданных читателю")
    public ResponseEntity<List<Issue>> getBooksByReader(@PathVariable Long id) {
        final List<Issue> issuesReader;
        issuesReader = issuerService.getIssuesByReader(id);
        if (issuesReader.size() < 1) {
            System.out.println("Выдачи по читателю не найдены");
            return ResponseEntity.notFound().build();
        } else {
            System.out.println("Читатель: " + readerService.getReaderById(id));
            return ResponseEntity.status(HttpStatus.OK).body(issuesReader);

        }
    }
}