package ru.dzheb.springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dzheb.springdatajpa.model.Issue;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue,Long> {
    List<Issue> findByReaderId(long readerId);
}