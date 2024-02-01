package ru.dzheb.springdatajpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.dzheb.springdatajpa.model.Role;
import ru.dzheb.springdatajpa.model.User;
import ru.dzheb.springdatajpa.repository.UserRepository;
import ru.dzheb.springdatajpa.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
//**
//        * 1. Для ресурсов, возвращающих HTML-страницы, реализовать авторизацию через login-форму.
//        * Остальные /api ресурсы, возвращающие JSON, закрывать не нужно!
//        * 2.1* Реализовать таблицы User(id, name, password) и Role(id, name), связанные многие ко многим
//        * 2.2* Реализовать UserDetailsService, который предоставляет данные из БД (таблицы User и Role)
//        * 3.3* Ресурсы выдачей (issue) доступны обладателям роли admin
//        * 3.4* Ресурсы читателей (reader) доступны всем обладателям роли reader
//        * 3.5* Ресурсы книг (books) доступны всем авторизованным пользователям
//        */
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
        // Инициализация пользователей с ролями
		//UserRepository userRepository = context.getBean(UserRepository.class);
		//userRepository.save(createReader());
		//userRepository.save(createAdmin());
//        Проверка наличия админв в базе
        UserService userService = context.getBean(UserService.class);
        System.out.println(userService.getUserByLogin("admin"));
    }
    private static User createReader() {
        User reader = new User();
        reader.setLogin("reader1");
        reader.setPassword("reader1");
        Role role = new Role();
        role.setRole("reader");
        List<Role> roles = Collections.singletonList(role);
        reader.setRoles(roles);
        return reader;
    }

    private static User createAdmin() {
        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("admin");
        Role role1 = new Role();
        role1.setRole("admin");
        Role role2 = new Role();
        role2.setRole("reader");
        List<Role> roles = new ArrayList<>();
        roles.add(role1);
        roles.add(role2);
        admin.setRoles(roles);
        return admin;

    }
}
