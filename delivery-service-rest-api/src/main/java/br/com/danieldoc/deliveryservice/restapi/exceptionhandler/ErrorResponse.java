package br.com.danieldoc.deliveryservice.restapi.exceptionhandler;

import java.util.List;

public record ErrorResponse(
        ErrorType code,
        String title,
        String description,
        List<Field> fields
) {

    public record Field(String name, String message) {
    }
}
