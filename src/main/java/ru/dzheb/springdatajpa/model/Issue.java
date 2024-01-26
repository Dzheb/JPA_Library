package ru.dzheb.springdatajpa.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "Выдача")
public class Issue {
    @Id
    @Schema(name = "Id выдачи")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;
    @Column(nullable = false)
    @Schema(name = "Id книги")
    private  long bookId;
    @Column(nullable = false)
    @Schema(name = "Id читателя")
    private  long readerId;

    /**
     * Дата выдачи и возврата
     */
    @Column
    @Schema(name = "Время и дата выдачи книги")
    private LocalDateTime issued_at;
    @Column
    @Schema(name = "Время и дата возврата книги")
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
