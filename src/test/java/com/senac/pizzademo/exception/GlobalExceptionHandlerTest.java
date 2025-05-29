package com.senac.pizzademo.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class GlobalExceptionHandlerTest {

    @Test
    void testHandleAllExceptions() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception ex = new Exception("Erro genérico");
        WebRequest request = null; // Pode ser mockado se necessário
        ResponseEntity<Object> response = handler.handleAllExceptions(ex, request);
        assertThat(response.getStatusCode().value()).isEqualTo(500);
        assertThat(String.valueOf(response.getBody())).contains("Erro genérico");
    }

    // O teste de validação será implementado após adicionar Bean Validation nas entidades/DTOs
}
