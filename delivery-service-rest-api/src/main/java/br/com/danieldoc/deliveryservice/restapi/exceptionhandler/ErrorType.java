package br.com.danieldoc.deliveryservice.restapi.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorType {

    INVALID_DATA("Dados inválidos"),
    MALFORMED_REQUEST("Request malformado"),
    BUSINESS_VIOLATION("Violação de regra de negócio"),
    NOT_FOUND("Recurso não encontrado"),
    SERVER_ERROR("Erro interno do servidor"),;

    private final String title;
}
