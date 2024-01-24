package ru.dzheb.springdatajpa.api;

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
public class IssuerController {

    private final IssuerService service;
// Возврат
    @PatchMapping("/{issueId}")
    public void returnBook(@PathVariable long issueId) {
        service.returnAt(issueId);
    }
// Выдача по id
    @GetMapping("/{id}")
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
