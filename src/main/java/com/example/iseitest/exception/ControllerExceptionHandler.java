package com.example.iseitest.exception;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {

//    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
    private static final String VALIDATION_ERROR = "Fields are not valid";

    @ExceptionHandler(IseiException.class)
    public ResponseEntity<ApiErrorResponse> iseiExceptionHandler(IseiException exception) {
        return buildException(exception, exception.getStatus());
    }

    @ExceptionHandler({
            RuntimeException.class, IllegalArgumentException.class, HibernateException.class,
            PropertyReferenceException.class, EntityNotFoundException.class, DataIntegrityViolationException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<ApiErrorResponse> commonExceptionHandler(Exception exception) {
        return buildException(exception);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiErrorResponse> validationExceptionHandler(ValidationException exception) {
        Throwable cause = exception.getCause();
        if (cause instanceof ConstraintViolationException) {
            return constraintViolationExceptionHandler((ConstraintViolationException) cause);
        } else if (cause instanceof IseiValidationException) {
            return buildException((Exception) cause);
        }
        return buildException(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        String exceptionId = IseiException.generateExceptionId();
//        logException(exception, exceptionId, securityService.getUserId());

        List<ValidationErrorField> errors = exception.getBindingResult().getAllErrors().stream()
                .map(p -> {
                    if (p instanceof FieldError) {
                        return new ValidationErrorField(((FieldError) p).getField(), p.getDefaultMessage());
                    } else {
                        return new ValidationErrorField(p.getObjectName(), p.getDefaultMessage());
                    }
                }).collect(Collectors.toList());

        ValidationErrorResponse apiException = ValidationResponseBuilder.builder(exceptionId)
                .withMessage(VALIDATION_ERROR)
                .withErrors(errors)
                .withCode(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> constraintViolationExceptionHandler(ConstraintViolationException e) {
        String exceptionId = IseiException.generateExceptionId();
//        logException(e, exceptionId, securityService.getUserId());

//        List<String> errors = e.getConstraintViolations().stream()
//                .map(ConstraintViolation::getMessage)
//                .toList();

        String message = e.getMessage();

        ApiErrorResponse apiException = ApiResponseBuilder.builder(exceptionId)
                .withMessage(String.join(", ", List.of(message)))
                .withErrors(List.of(message))
                .withCode(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> accessDeniedExceptionHandler(AccessDeniedException e) {
        String exceptionId = IseiException.generateExceptionId();
//        logException(e, exceptionId, securityService.getUserId());

        ApiErrorResponse apiException = ApiResponseBuilder.builder(exceptionId)
                .withMessage(e.getMessage())
                .withError(e.getMessage())
                .withCode(HttpStatus.UNAUTHORIZED.value())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ApiErrorResponse> buildException(HttpStatus status, String message) {
        String exceptionId = IseiException.generateExceptionId();
//        logException(null, exceptionId, securityService.getUserId());

        ApiErrorResponse apiException = ApiResponseBuilder.builder(exceptionId)
                .withMessage(message)
                .withError(message)
                .build();

        return new ResponseEntity<>(apiException, status);
    }

    private ResponseEntity<ApiErrorResponse> buildException(IseiException e, HttpStatus status) {
        ApiErrorResponse apiException = ApiResponseBuilder.builder(e.getId())
                .withCode(e.getCode().getIntValue())
                .withMessage(e.getMessage())
                .withError(e.getMessage())
                .build();

        return new ResponseEntity<>(apiException, status);
    }

    private ResponseEntity<ApiErrorResponse> buildException(Exception e) {
        String exceptionId = IseiException.generateExceptionId();
//        logException(e, exceptionId, securityService.getUserId());

        ApiErrorResponse apiException = ApiResponseBuilder.builder(exceptionId)
                .withCode(Code.SYSTEM_ERROR.getIntValue())
                .withMessage(e.getMessage())
                .withError(e.getMessage())
                .build();

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

//    private void logException(Exception exception, String exceptionId, String userId) {
//        logger.error("Exception: {}, Logged in user: {}", exceptionId, userId, exception);
//    }
}
