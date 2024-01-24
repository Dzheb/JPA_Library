package ru.dzheb.springdatajpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dzheb.springdatajpa.model.Reader;
import ru.dzheb.springdatajpa.repository.ReaderRepository;

import java.util.List;

@Service
public class ReaderServiceImpl implements ReaderService{
    private final ReaderRepository repository;
    @Autowired
    public ReaderServiceImpl(ReaderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Reader getReaderById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Long addReader(String name) {
        Reader reader = new Reader(name);
        repository.save(reader);
        return reader.getId();
    }

    @Override
    public String deleteReader(long id) {
        Reader reader = getReaderById(id);
        if (reader != null) {
            repository.deleteById(id);
            return "Читатель id = " + id + " удалена";
        } else {
            return "Читатель id = " + id + " не найден";
        }

    }

    @Override
    public List<Reader> allReaders() {
        return repository.findAll();
    }
}
