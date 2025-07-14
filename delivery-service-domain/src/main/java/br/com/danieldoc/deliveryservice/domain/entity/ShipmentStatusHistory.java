package br.com.danieldoc.deliveryservice.domain.entity;

import br.com.danieldoc.deliveryservice.domain.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shipment_status_history")
@Entity
public class ShipmentStatusHistory {

    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ShipmentStatus status;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Shipment shipment;

    @Setter(AccessLevel.NONE)
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = OffsetDateTime.now();
    }
}
