package br.com.danieldoc.deliveryservice.restapi.api.v1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {

    @Size(max = 255)
    @NotBlank
    private String street;

    @Size(max = 30)
    @NotBlank
    private String number;

    @Size(max = 60)
    @NotBlank
    private String neighborhood;

    @Size(max = 20)
    private String complement;

    @Size(max = 100)
    @NotBlank
    private String city;

    @Size(min = 2, max = 2)
    @NotBlank
    private String state;

    @Size(min = 8, max = 8)
    @NotBlank
    private String zipCode;

    @Size(max = 255)
    private String referencePoint;
}
