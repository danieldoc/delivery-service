package br.com.danieldoc.deliveryservice.restapi.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorType {

    INVALID_DATA("Invalid Data"),
    MALFORMED_REQUEST("Request in malformed format"),
    BUSINESS_VIOLATION("Business rule violation"),
    NOT_FOUND("Resource not found"),
    SERVER_ERROR("Server error"),;

    private final String title;
}
