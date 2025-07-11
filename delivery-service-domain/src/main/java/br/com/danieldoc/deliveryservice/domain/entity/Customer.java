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
public class Customer {

    @Column(name = "customer_document", length = 30, nullable = false)
    private String document;

    @Column(name = "customer_full_name", nullable = false)
    private String fullName;

    @Column(name = "customer_cellphone", length = 30)
    private String cellphone;

    @Column(name = "customer_email", length = 100)
    private String email;
}
