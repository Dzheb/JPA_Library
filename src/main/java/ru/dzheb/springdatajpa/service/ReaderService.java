package ru.dzheb.springdatajpa.service;

import ru.dzheb.springdatajpa.model.Reader;
import java.util.List;


public interface ReaderService {

    public Reader getReaderById(long id);
    public Long addReader(String name);

    public String deleteReader(long id);

    public List<Reader> allReaders();
}

