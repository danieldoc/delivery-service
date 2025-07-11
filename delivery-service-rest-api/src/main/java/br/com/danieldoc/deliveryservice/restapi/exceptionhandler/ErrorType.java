package br.com.danieldoc.deliveryservice.restapi.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorType {

    INVALID_DATA("Dados inválidos"),
    MALFORMED_REQUEST("Requisição em formato inválido"),
    BUSINESS_VIOLATION("Violação de Negócio"),
    NOT_FOUND("Recurso não encontrado"),
    INTERNAL_SERVER_ERROR("Erro no servidor");

    private final String title;
}
