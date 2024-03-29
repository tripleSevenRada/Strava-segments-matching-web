package com.matching.segmentsmatching.ControllerAdvice;

import com.matching.segmentsmatching.exceptions.MatchingValidityException;
import com.matching.segmentsmatching.rest.Errors;
import com.matching.segmentsmatching.rest.JustMessage;
import com.matching.segmentsmatching.rest.ValidationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
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

    The @ControllerAdvice annotation was first introduced in Spring 3.2. It allows you to
    handle exceptions across the whole application, not just to an individual controller.
    You can think of it as an interceptor of exceptions
    thrown by methods ANNOTATED with @RequestMapping or one of the shortcuts.
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
    @ExceptionHandler(MatchingValidityException.class)
    protected ResponseEntity<Object> handleMatchingValidityException(MatchingValidityException mve){
        JustMessage message = new JustMessage(mve.getMessage());
        return ResponseEntity.badRequest().body(message);
    }
    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException hcee) {
        JustMessage message = new JustMessage(hcee.getMessage());
        return ResponseEntity.status(hcee.getStatusCode()).body(message);
    }
    @ExceptionHandler(HttpServerErrorException.class)
    protected ResponseEntity<Object> handleHttpServerErrorException(HttpServerErrorException hsee) {
        JustMessage message = new JustMessage(hsee.getMessage());
        return ResponseEntity.status(hsee.getStatusCode()).body(message);
    }
}
