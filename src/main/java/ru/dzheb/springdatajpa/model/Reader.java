package ru.dzheb.springdatajpa.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "readers")
@Schema(name="Читатель")
public class Reader {
    @Id
    @Schema(name="Id читателя")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    @Schema(name="Имя читателя")
    private String name;
    public Reader (String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return this.id + ".   " + '"' + this.name + '"';
    }
    public static Reader ofName(String name) {
        Reader reader = new Reader();
        reader.setName(name);
        return reader;
    }
}
