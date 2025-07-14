package br.com.danieldoc.deliveryservice.restapi.exceptionhandler;

import br.com.danieldoc.deliveryservice.domain.exception.DeliveryServiceBusinessException;
import br.com.danieldoc.deliveryservice.domain.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(DeliveryServiceBusinessException.class)
    public ResponseEntity<ErrorResponse> handleDeliveryServiceBusinessException(DeliveryServiceBusinessException e) {
        final ErrorResponse errorResponse = new ErrorResponse(ErrorType.BUSINESS_VIOLATION,
                ErrorType.BUSINESS_VIOLATION.getTitle(),
                e.getMessage(),
                null);
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex,
                                                                    HttpHeaders headers,
                                                                    HttpStatusCode status,
                                                                    WebRequest request) {
        String detail = messageSource.getMessage("exception.resourceNotFound", null, LocaleContextHolder.getLocale());
        final ErrorResponse errorResponse = new ErrorResponse(ErrorType.NOT_FOUND,
                ErrorType.NOT_FOUND.getTitle(),
                detail,
                null);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatusCode status,
                                                                         WebRequest request) {
        String detail = messageSource.getMessage("exception.httpMethodNotSupported", null, LocaleContextHolder.getLocale());
        final ErrorResponse errorResponse = new ErrorResponse(ErrorType.MALFORMED_REQUEST,
                ErrorType.MALFORMED_REQUEST.getTitle(),
                detail,
                null);

        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        String detail = messageSource.getMessage("exception.malformedRequest", null, LocaleContextHolder.getLocale());
        final ErrorResponse errorResponse = new ErrorResponse(ErrorType.MALFORMED_REQUEST,
                ErrorType.MALFORMED_REQUEST.getTitle(),
                detail,
                null);

        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult().getAllErrors(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(
            HandlerMethodValidationException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<ObjectError> errors = ex.getBeanResults().stream()
                .flatMap(bean -> bean.getAllErrors().stream())
                .toList();

        return handleValidationInternal(ex, errors, headers, status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        final ErrorResponse errorResponse = new ErrorResponse(ErrorType.NOT_FOUND,
                ErrorType.NOT_FOUND.getTitle(),
                e.getMessage(),
                null);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Ocorreu uma exceção não tratada: {}", ex.getMessage(), ex);

        String message = messageSource.getMessage("exception.internalServerError", null, LocaleContextHolder.getLocale());
        final ErrorResponse errorResponse = new ErrorResponse(ErrorType.SERVER_ERROR, ErrorType.SERVER_ERROR.getTitle(), message, null);

        return ResponseEntity
                .internalServerError()
                .body(errorResponse);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex,
                                                            List<? extends ObjectError> objectErrors,
                                                            HttpHeaders headers,
                                                            HttpStatusCode status,
                                                            WebRequest request) {
        String detail = messageSource.getMessage("exception.invalidData", null, LocaleContextHolder.getLocale());

        List<ErrorResponse.Field> problemFields = objectErrors.stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError instanceof FieldError fieldError
                            ? fieldError.getField()
                            : objectError.getObjectName();

                    return new ErrorResponse.Field(name, message);
                })
                .toList();

        final ErrorResponse errorResponse = new ErrorResponse(ErrorType.INVALID_DATA, ErrorType.INVALID_DATA.getTitle(), detail, problemFields);
        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }
}
