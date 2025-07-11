package br.com.danieldoc.deliveryservice.restapi.api.v1.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    private String document;
    private String fullName;
    private String cellphone;
    private String email;
}
