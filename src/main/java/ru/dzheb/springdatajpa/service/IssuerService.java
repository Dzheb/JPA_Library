package ru.dzheb.springdatajpa.service;

import ru.dzheb.springdatajpa.api.IssueRequest;
import ru.dzheb.springdatajpa.model.Book;
import ru.dzheb.springdatajpa.model.Issue;
import ru.dzheb.springdatajpa.model.IssueUI;

import java.util.List;

public interface IssuerService {

    Issue issue(IssueRequest request);

    Issue getIssueById(Long id);

    void returnAt(long issueId);

    List<Issue> getIssuesByReader(Long id);

    List<Book> getIssuesByReaderUI(Long id);


    List<IssueUI> allIssues();

}