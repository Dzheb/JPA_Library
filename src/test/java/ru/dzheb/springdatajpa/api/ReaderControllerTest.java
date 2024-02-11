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
import ru.dzheb.springdatajpa.model.Reader;
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
    ReaderControllerTest.JUnitReaderResponse request = new ReaderControllerTest.JUnitReaderResponse();
    request.setName("Mike Penn");
    request.setId(maxId + 1L);
    webTestClient.post()
            .uri("/api/reader")
            .body(Mono.just(request), JSONObject.class)
            .exchange()
            .expectStatus().isOk()
            .expectBody(new ParameterizedTypeReference<Long>() {
            })
            .returnResult().equals(maxId+1L);
  }
  @Test
  void testDeleteById() {
    Long expected = jdbcTemplate.queryForObject("select max(id) from readers", Long.class);
    webTestClient.delete()
            .uri("/api/reader/" + expected)
            .exchange()
            .expectStatus().isOk();
    Assertions.assertFalse(readerRepositoryTest.existsById(expected));
  }


}
