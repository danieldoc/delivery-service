package br.com.danieldoc.deliveryservice.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Embeddable
public class Address {

    @Column(name = "address_street", nullable = false)
    private String street;

    @Column(name = "address_number", length = 30, nullable = false)
    private String number;

    @Column(name = "address_neighborhood", length = 60, nullable = false)
    private String neighborhood;

    @Column(name = "address_complement", length = 20)
    private String complement;

    @Column(name = "address_city", length = 100, nullable = false)
    private String city;

    @Column(name = "address_state", length = 60, nullable = false)
    private String state;

    @Column(name = "address_zip_code", length = 20, nullable = false)
    private String zipCode;

    @Column(name = "address_reference_point")
    private String referencePoint;
}
