package com.oleynikovee.atm.web.handler;

import com.oleynikovee.atm.model.core.ResponseObject;
import com.oleynikovee.atm.model.error.ApplicationException;
import com.oleynikovee.atm.model.error.ErrorCode;
import com.oleynikovee.atm.model.error.ErrorMessage;
import com.oleynikovee.atm.model.error.RestCallException;
import com.oleynikovee.atm.web.controller.UserController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;


import java.util.List;

import static com.oleynikovee.atm.model.error.ErrorCode.INPUT_VALIDATION;
import static com.oleynikovee.atm.model.error.ErrorCode.TECHNICAL_ERROR;
import static com.oleynikovee.atm.model.error.ErrorMessage.buildError;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@Slf4j
@RequiredArgsConstructor
@ControllerAdvice(basePackageClasses = UserController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseObject<Object>> handleException(Exception ex, HandlerMethod handlerMethod) {
        log.error("Uncaught exception handled in Controller: {}, message: {}", handlerMethod.getMethod().getDeclaringClass().getSimpleName(), ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                       .body(buildResponseObject(TECHNICAL_ERROR, "Technical error"));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ResponseObject<Object>> handleUserException(ApplicationException e) {
        var code = e.getErrorCode();
        String message = e.getMessage();
        log.info("AccentUException: {}, code: {}", message, code);
        return ResponseEntity.status(HttpStatus.valueOf(code.getHttpCode()))
                       .body(buildResponseObject(code, message));
    }

    @ExceptionHandler(RestCallException.class)
    public ResponseEntity<ResponseObject<?>> handleRestCallException(RestCallException ex) {
        ResponseObject<?> response = ex.getResponse();
        log.error("RestCallException message: {}, errors: {}", ex.getMessage(), response.getErrors());
        return ResponseEntity.status(HttpStatus.valueOf(response.getHttpCode()))
                       .body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseObject<Object>> handleAccessDeniedException(AccessDeniedException e, HandlerMethod handlerMethod) {
        log.error("AccessDeniedException handled in service: {}, message: {}",
                  handlerMethod.getMethod().getDeclaringClass().getSimpleName(), e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                       .body(buildResponseObject(ErrorCode.FORBIDDEN, e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseObject<Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HandlerMethod handlerMethod) {
        log.error("HttpMessageNotReadableException handled in service: {}, message: {}",
                  handlerMethod.getMethod().getDeclaringClass().getSimpleName(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body(buildResponseObject(ErrorCode.BAD_REQUEST, "JSON parse error"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HandlerMethod method) {
        log.error("MethodArgumentNotValidException handled in service: {}, message: {}", method.getMethod().getDeclaringClass().getSimpleName(), ex.getMessage());
        List<ErrorMessage> errors = ex.getBindingResult()
                                            .getAllErrors().stream()
                                            .map(e -> buildError(INPUT_VALIDATION, e.getDefaultMessage(), ((FieldError) e).getField()))
                                            .collect(toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body(ResponseObject.builder()
                                     .fail(errors)
                                     .build());
    }

    /*@ExceptionHandler(IdpException.class)
    public ResponseEntity<ResponseObject<Object>> handleIdpException(IdpException e) {
        log.error("IdpException: {}, code: {}", e.getMessage(), e.getCode());
        HttpStatus status = HttpStatus.valueOf(e.getCode());
        return ResponseEntity.status(status)
                       .body(buildResponseObject(null, e.getMessage()));
    }*/

    private ResponseObject<Object> buildResponseObject(ErrorCode errorCode, String message) {
        return ResponseObject.builder()
                       .fail(buildError(errorCode, message))
                       .build();
    }
}
