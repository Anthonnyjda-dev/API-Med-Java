package br.com.anthonny.hoteljpa.erro;

import jakarta.validation.ConstraintViolationException;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ApiExceptionHandler {
    public record Erro(String erro, List<String> detalhes) {}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Erro> entradaInvalida(MethodArgumentNotValidException ex) {
        var detalhes = ex.getBindingResult().getFieldErrors().stream()
                .map(campo -> campo.getField() + ": " + campo.getDefaultMessage()).toList();
        return ResponseEntity.badRequest().body(new Erro("Entrada inválida", detalhes));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class,
            ConstraintViolationException.class})
    public ResponseEntity<Erro> formatoInvalido(Exception ex) {
        return ResponseEntity.badRequest().body(new Erro("Entrada inválida", List.of(ex.getMessage())));
    }

    @ExceptionHandler(RecursoNaoEncontrado.class)
    public ResponseEntity<Erro> naoEncontrado(RecursoNaoEncontrado ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erro(ex.getMessage(), List.of()));
    }

    @ExceptionHandler({Conflito.class, DataIntegrityViolationException.class})
    public ResponseEntity<Erro> conflito(Exception ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new Erro(ex instanceof Conflito ? ex.getMessage() : "Conflito de dados; verifique o e-mail informado", List.of()));
    }
}
