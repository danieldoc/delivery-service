package br.com.danieldoc.deliveryservice.restapi.api.v1.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {

    private String street;
    private String number;
    private String neighborhood;
    private String complement;
    private String city;
    private String state;
    private String zipCode;
    private String referencePoint;
}
