package ru.dzheb.springdatajpa.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "books")
@Schema(name = "Книга")
public class Book {
    @Id
    @Schema(name = "Id книги")
    @GeneratedValue(strategy =
    GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    @Schema(name = "Имя книги",minimum ="3",maximum = "100")
    private  String name;

    public Book(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.id + ".   " + '"'+this.name+'"' ;
    }
}
