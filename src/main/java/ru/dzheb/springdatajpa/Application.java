package ru.dzheb.springdatajpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Домашнее задание:
 * 1. Подключить базу данных к проекту "библиотека", который разрабатывали на прошлых уроках.
 * 1.1 Подключить spring-boot-starter-data-jpa (и необходимый драйвер) и указать параметры соединения в application.yml
 * 1.2 Для книги, читателя и факта выдачи описать JPA-сущности
 * 1.3 Заменить самописные репозитории на JPA-репозитории
 *
 * Замечание: базу данных можно использовать любую (h2, mysql, postgres).
 */
// Семинар 6
//1. Подключить OpenAPI 3 и swagger к проекту с
//        библиоткой
//        2. Описать все контроллеры, эндпоинты и
//        возвращаемые тела с помощью аннотаций
//        OpenAPI 3
//        3. В качестве результата, необходимо
//        прислать скриншот(ы) страницы swagger
//        (с ручками)
//
//        Доп. задание (сдавать не нужно):
//        придумать какие-то доменные сервисы
//        (по типу библиотеки и заметок) и
//        попытаться спроектировать его API.
// http://localhost:9000/swagger-ui.html
//    http://localhost:9000/v3/api-docs
@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties(ReaderProperties.class)
public class Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        ReaderProperties readerProperties = context.getBean(ReaderProperties.class);
        log.info("max-allowed-books: {}", readerProperties.getMaxAllowedBooks());
        log.info("tags: {}", readerProperties.getTags());

    }
}
