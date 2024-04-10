package fastcampus.ad.legacy.api.exception;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorAdvice {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<?> handleNotFoundException() {
    return ResponseEntity.notFound().build();
  }
}
