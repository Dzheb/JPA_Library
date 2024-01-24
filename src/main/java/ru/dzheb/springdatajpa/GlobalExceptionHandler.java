package ru.dzheb.springdatajpa;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@Component
@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<String> notFound(NoSuchElementException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

//  @ExceptionHandler(Exception.class)
//  public ResponseEntity<String> exc(Exception e) {
//    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//  }

}
