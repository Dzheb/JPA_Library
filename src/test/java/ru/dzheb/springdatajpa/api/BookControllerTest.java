package ru.dzheb.springdatajpa.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.h2.util.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.dzheb.springdatajpa.JUnitSpringBootBase;
import ru.dzheb.springdatajpa.model.Book;
import ru.dzheb.springdatajpa.repository.BookRepositoryTest;

import java.util.List;
import java.util.Objects;

class BookControllerTest extends JUnitSpringBootBase {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    BookRepositoryTest bookRepositoryTest;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Data
    @NoArgsConstructor
    static class JUnitBookResponse {
        private Long id;
        private String name;

    }

    @Test
    void testFindByIdSuccess() {
        // подготовил данные
        Book expected = bookRepositoryTest.save(Book.ofName("random"));

        JUnitBookResponse responseBody = webTestClient.get()
                .uri("/api/book/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitBookResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getName(), responseBody.getName());
    }

    @Test
    void testFindByIdNotFound() {
        Long maxId = jdbcTemplate.queryForObject("select max(id) from books", Long.class);

        webTestClient.get()
                .uri("/api/book/" + maxId + 1)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testGetAll() {
        // подготовил данные
        bookRepositoryTest.saveAll(List.of(
                Book.ofName("first"),
                Book.ofName("second")
        ));

        List<Book> expected = bookRepositoryTest.findAll();

        List<JUnitBookResponse> responseBody = webTestClient.get()
                .uri("/api/book")
                // .retrieve
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitBookResponse>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(expected.size(), responseBody.size());
        for (JUnitBookResponse customerResponse : responseBody) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), customerResponse.getId()))
                    .anyMatch(it -> Objects.equals(it.getName(), customerResponse.getName()));
            Assertions.assertTrue(found);
        }
    }

    @Test
//  @Disabled
    void testSave() {
        Long maxId = jdbcTemplate.queryForObject("select max(id) from books", Long.class);
        JUnitBookResponse request = new JUnitBookResponse();
        request.setName("Money");
        request.setId(maxId + 1L);
        webTestClient.post()
                .uri("/api/book")
                .body(Mono.just(request), JSONObject.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Long>() {
                })
                .returnResult().equals(maxId+1L);
    }

    @Test
    void testDeleteById() {
        Long expected = jdbcTemplate.queryForObject("select max(id) from books", Long.class);
        webTestClient.delete()
                .uri("/api/book/" + expected)
                .exchange()
                .expectStatus().isOk();
        Assertions.assertFalse(bookRepositoryTest.existsById(expected));
    }

}
