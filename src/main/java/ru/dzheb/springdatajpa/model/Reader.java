package ru.dzheb.springdatajpa.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "readers")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String name;
    public Reader (String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return this.id + ".   " + '"' + this.name + '"';
    }

}
