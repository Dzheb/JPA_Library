package ru.dzheb.springdatajpa.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Запрос на выдачу
 */
@Data
@Schema(name = "Запрос на выдачу")
public class IssueRequest {

  /**
   * Идентификатор читателя
   */
  @Schema(name = "Id читателя")
  private long readerId;

  /**
   * Идентификатор книги
   */
  @Schema(name = "Id книги")
  private long bookId;

}
