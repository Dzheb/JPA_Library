package ru.dzheb.springdatajpa.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IssueUI {
    long id;
    String bookName;
    String readerName;
    LocalDateTime issued_at;
    LocalDateTime returned_at;

    public IssueUI(long id, String bookName,
                   String readerName,
                   LocalDateTime issued_at,
                   LocalDateTime returned_at) {
        this.id = id;
        this.bookName = bookName;
        this.readerName = readerName;
        this.issued_at = issued_at;
        this.returned_at = returned_at;
    }
    @Override
    public String toString() {
        return "# "+this.id + ", Название: " + '"'+this.bookName+'"' ;
    }
}
