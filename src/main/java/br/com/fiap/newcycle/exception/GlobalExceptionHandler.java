package br.com.fiap.newcycle.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiError handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        log.warn("Recurso não encontrado: {}", ex.getMessage());
        return new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                req.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        StringBuilder sb = new StringBuilder("Campo(s) inválido(s): ");
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("; ");
        }
        return new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                sb.toString(),
                req.getRequestURI());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ApiError handleConstraint(ConstraintViolationException ex, HttpServletRequest req) {
        return new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Constraint Violation",
                ex.getMessage(),
                req.getRequestURI());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiError handleBadJson(HttpMessageNotReadableException ex, HttpServletRequest req) {
        return new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Malformed JSON",
                ex.getMostSpecificCause().getMessage(),
                req.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ApiError handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest req) {
        log.warn("Violação de integridade: {}", ex.getMostSpecificCause().getMessage());
        return new ApiError(
                HttpStatus.CONFLICT.value(),
                "Data Integrity Violation",
                "Operação viola restrições de banco de dados.",
                req.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ApiError handleGeneric(Exception ex, HttpServletRequest req) {
        log.error("Erro inesperado", ex);
        return new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Ocorreu um erro inesperado. Contate o suporte.",
                req.getRequestURI());
    }
}
