package ru.dzheb.springdatajpa.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.dzheb.springdatajpa.JUnitSpringBootBase;
import ru.dzheb.springdatajpa.model.Book;
import ru.dzheb.springdatajpa.model.Reader;
import ru.dzheb.springdatajpa.repository.BookRepositoryTest;
import ru.dzheb.springdatajpa.repository.ReaderRepositoryTest;

import java.util.List;
import java.util.Objects;

class ReaderControllerTest extends JUnitSpringBootBase {

   @Autowired
  WebTestClient webTestClient;
  @Autowired
  ReaderRepositoryTest readerRepositoryTest;
  @Autowired
  JdbcTemplate jdbcTemplate;

  @Data
  @NoArgsConstructor
  static class JUnitReaderResponse {
    private Long id;
    private String name;
  }

  @Test
  void testFindByIdSuccess() {
    // подготовил данные
    Reader expected = readerRepositoryTest.save(Reader.ofName("Random"));

    ReaderControllerTest.JUnitReaderResponse responseBody = webTestClient.get()
      .uri("/api/reader/" + expected.getId())
      .exchange()
      .expectStatus().isOk()
      .expectBody(ReaderControllerTest.JUnitReaderResponse.class)
      .returnResult().getResponseBody();

    Assertions.assertNotNull(responseBody);
    Assertions.assertEquals(expected.getId(), responseBody.getId());
    Assertions.assertEquals(expected.getName(), responseBody.getName());
  }

   @Test
  void testGetAll() {
    // подготовил данные
    readerRepositoryTest.saveAll(List.of(
      Reader.ofName("First"),
      Reader.ofName("Second")
    ));

    List<Reader> expected = readerRepositoryTest.findAll();

    List<JUnitReaderResponse> responseBody = webTestClient.get()
      .uri("/api/reader")
      // .retrieve
      .exchange()
      .expectStatus().isOk()
      .expectBody(new ParameterizedTypeReference<List<JUnitReaderResponse>>() {
      })
      .returnResult()
      .getResponseBody();

    Assertions.assertEquals(expected.size(), responseBody.size());
    for (JUnitReaderResponse customerResponse : responseBody) {
      boolean found = expected.stream()
        .filter(it -> Objects.equals(it.getId(), customerResponse.getId()))
        .anyMatch(it -> Objects.equals(it.getName(), customerResponse.getName()));
      Assertions.assertTrue(found);
    }
  }

  @Test
//  @Disabled
  void testSave() {
    Long maxId = jdbcTemplate.queryForObject("select max(id) from readers", Long.class);

    JUnitReaderResponse request = new JUnitReaderResponse();
    request.setName("Max");
    request.setId(maxId+1L);

    JUnitReaderResponse responseBody = webTestClient.post()
      .uri("/api/reader")
      .bodyValue(request)
      .exchange()
      .expectStatus().isCreated()
      .expectBody(JUnitReaderResponse.class)
      .returnResult().getResponseBody();

    Assertions.assertNotNull(responseBody);
    Assertions.assertNotNull(responseBody.getId());
    Assertions.assertTrue(readerRepositoryTest.findById(request.getId()).isPresent());
  }

  @Test
  void testDeleteById() {
    Long maxId = jdbcTemplate.queryForObject("select max(id) from readers", Long.class);
    Long expected = maxId + 1;
    JUnitReaderResponse responseBody = webTestClient.delete()
            .uri("/api/book/" + 1)
            .exchange()
            .expectStatus().isOk()
            .expectBody(JUnitReaderResponse.class)
            .returnResult().getResponseBody();

    Assertions.assertNotNull(responseBody);
    Assertions.assertNotNull(responseBody.getId());
    Assertions.assertFalse(readerRepositoryTest.findById(expected).isPresent());
  }

}
