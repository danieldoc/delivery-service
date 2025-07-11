package br.com.danieldoc.deliveryservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
public class Shipment {

    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private Integer packageQuantity;

    @Column(nullable = false)
    private OffsetDateTime deliveryDeadline;

    @Embedded
    private Customer customer;

    @Embedded
    private Address address;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @Version
    @Column(nullable = false)
    private Integer version;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private boolean deleted;

    @PrePersist
    private void prePersist() {
        this.code = UUID.randomUUID().toString();
        this.deleted = false;
    }

    public void deleteIt() {
        this.deleted = true;
    }
}
