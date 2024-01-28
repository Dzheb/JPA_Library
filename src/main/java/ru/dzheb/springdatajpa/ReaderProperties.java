package ru.dzheb.springdatajpa;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@ConfigurationProperties("application.reader")
public class ReaderProperties {

    private Integer maxAllowedBooks;

    private Map<String, String> tags;

}
