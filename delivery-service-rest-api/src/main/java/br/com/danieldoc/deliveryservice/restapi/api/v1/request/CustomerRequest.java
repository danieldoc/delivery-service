package br.com.danieldoc.deliveryservice.restapi.api.v1.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {

    @NotBlank
    @Size(min = 11, max = 11)
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
