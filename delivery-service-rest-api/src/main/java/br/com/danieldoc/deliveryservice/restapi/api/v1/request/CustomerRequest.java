package br.com.danieldoc.deliveryservice.restapi.api.v1.request;

import br.com.danieldoc.deliveryservice.domain.constraintvalidator.CPF;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {

    @NotBlank
    @CPF
    private String document;

    @NotBlank
    @Size(min = 2, max = 100)
    private String fullName;

    @Size(min = 11, max = 15)
    private String cellphone;

    @Email
    @Size(min = 3, max = 100)
    private String email;
}
