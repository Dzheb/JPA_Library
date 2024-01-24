package ru.dzheb.springdatajpa.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy =
    GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private  String name;

    public Book(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.id + ".   " + '"'+this.name+'"' ;
    }
}
