package ru.dzheb.springdatajpa.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Запись о факте выдачи книги (в БД)
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "issues")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;
    @Column(nullable = false)
    private  long bookId;
    @Column(nullable = false)
    private  long readerId;

    /**
     * Дата выдачи и возврата
     */
    @Column
    private LocalDateTime issued_at;
    @Column
    private LocalDateTime returned_at;

    public Issue(long bookId, long readerId) {
        this.bookId = bookId;
        this.readerId = readerId;
        this.issued_at = LocalDateTime.now();
        this.returned_at = null;

    }

    public void setReturned_at(LocalDateTime returned_at) {
        this.returned_at = returned_at;
    }
}
