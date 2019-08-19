package com.matching.segmentsmatching.ControllerAdvice;

import com.matching.segmentsmatching.rest.Errors;
import com.matching.segmentsmatching.rest.ValidationError;
import com.matching.segmentsmatching.rest.WebClientError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    /*
    https://www.baeldung.com/exception-handling-for-rest-with-spring

    Spring 3.2 brings support for a global @ExceptionHandler with the @ControllerAdvice annotation.
    This enables a mechanism that breaks away from the older MVC model and
    makes use of ResponseEntity along with the type safety and flexibility of @ExceptionHandler:

    https://stackoverflow.com/questions/51991992/getting-ambiguous-exceptionhandler-method-mapped-for-methodargumentnotvalidexce?rq=1
    https://github.com/spring-projects/spring-framework/blob/master/spring-webmvc/src/main/java/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.java

    ResponseEntityExceptionHandler:

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleExceptionInternal(ex, null, headers, status, request);
    }
    */

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        Errors errors = new Errors();
        List<ValidationError> errorList = result.getFieldErrors().stream().map(e -> {
            return new ValidationError(e.getField(), e.getDefaultMessage());
        }).collect(Collectors.toList());
        errors.setErrors(errorList);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(WebClientException.class)
    protected ResponseEntity<WebClientError> handleWebClientException(
            WebClientException ex) {
        return ResponseEntity.badRequest().body(new WebClientError(ex.getMessage()));
    }

}
