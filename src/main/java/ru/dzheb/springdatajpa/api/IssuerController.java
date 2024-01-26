package ru.dzheb.springdatajpa.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dzheb.springdatajpa.model.Issue;
import ru.dzheb.springdatajpa.service.IssuerService;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/issue")
@Tag(name = "Issue")
public class IssuerController {

    private final IssuerService service;
// Возврат

    @PatchMapping("/{issueId}")
    @Operation(summary = "return book to the library"
            ,description = "Возврат книги в библиотеку путём" +
            " добавления даты возврата в выдачу")
    public void returnBook(@PathVariable long issueId) {
        service.returnAt(issueId);
    }
// Выдача по id
    @GetMapping("/{id}")
    @Operation(summary = "get issue by Id"
            ,description = "Нахождение выдачи книги по Id")
    public ResponseEntity<Issue> getIssue(@PathVariable Long id) {
        final Issue issue;
        issue = service.getIssueById(id);
        if (issue == null) {
            System.out.println("Выдача: не найдена");
            return ResponseEntity.notFound().build();
        } else {
            System.out.println("Выдача: " + service.getIssueById(id));
            return ResponseEntity.status(HttpStatus.OK).body(issue);
        }
    }
// Выдача
    @PostMapping
    @Operation(summary = "add issue of book"
            ,description = "Выдача книги читателю")
    public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) {
        log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());
        final Issue issue;
        try {
            issue = service.issue(request);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Книга с идентификатором \"" + request.getBookId() +
                    "\"находится на руках");
        }
        if (issue != null)
            return ResponseEntity.status(HttpStatus.OK).body(issue);
        else
            throw new NoSuchElementException("Превышен лимит" +
                    " выдачи книг по читателю");
    }
}
