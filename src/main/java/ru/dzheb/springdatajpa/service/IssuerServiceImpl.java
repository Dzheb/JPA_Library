package ru.dzheb.springdatajpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.dzheb.springdatajpa.api.IssueRequest;
import ru.dzheb.springdatajpa.model.Book;
import ru.dzheb.springdatajpa.model.Issue;
import ru.dzheb.springdatajpa.model.IssueUI;
import ru.dzheb.springdatajpa.repository.IssueRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssuerServiceImpl implements IssuerService {

    private final BookService bookService;
    private final ReaderService readerService;
    private final IssueRepository issueRepository;
    //  application.max-allowed-books default = 1
    @Value("${application.max-allowed-books:1}")
    private int maxAllowedBooks;


    public Issue getIssueById(Long id) {
        return issueRepository.findById(id).orElse(null);
    }

    public Issue issue(IssueRequest request) {
        if (bookService.getBookById(request.getBookId()) == null) {
            throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
        }
        if (readerService.getReaderById(request.getReaderId()) == null) {
            throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
        }
        if (issueRepository.findAll().stream()
                .filter(it -> it.getBookId() == request.getBookId()
                        && it.getReturned_at() == null)
                .toList().size() != 0) {
            System.out.println("Книга с идентификатором \"" + request.getBookId() +
                    "\"находится на руках");
            throw new NoSuchElementException("Книга с идентификатором \"" + request.getBookId() +
                    "\"находится на руках");
        }
        // можно проверить, что у читателя нет книг на руках (или его лимит не превышает в Х книг)
        List<Issue> books = issueRepository.findByReaderId(request.getReaderId());
        if (books.size() < maxAllowedBooks) {
            Issue issue = new Issue(request.getBookId(), request.getReaderId());
            issueRepository.save(issue);
            return issue;
        } else {
            return null;
        }

    }

    public void returnAt(long issueId) {
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue != null) {
            issue.setReturned_at(LocalDateTime.now());
            issueRepository.saveAndFlush(issue);
        } else throw new NoSuchElementException("Нет такой выдачи");
    }

    public List<Issue> getIssuesByReader(Long id) {
        return issueRepository.findByReaderId(id);
    }

    public List<Book> getIssuesByReaderUI(Long id) {
        return issueRepository.findByReaderId(id)
                .stream().filter(it -> it.getReturned_at() == null).map(it -> bookService
                        .getBookById(it.getBookId()))
                .collect(Collectors.toList());
    }

    public List<IssueUI> allIssues() {
        List<IssueUI> issueUIS = new ArrayList<>();
        List<Issue> issues = issueRepository.findAll();
        for (Issue issue : issues) {
            IssueUI issueUI = new IssueUI(issue.getId(),
                    bookService.getBookById(issue.getBookId())
                            .getName(),
                    readerService.getReaderById(issue
                            .getReaderId()).getName(),
                    issue.getIssued_at(),
                    issue.getReturned_at());
            issueUIS.add(issueUI);
        }
        return issueUIS;
    }

}
