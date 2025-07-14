package br.com.danieldoc.deliveryservice.domain.entity;

import br.com.danieldoc.deliveryservice.domain.enums.ShipmentStatus;
import br.com.danieldoc.deliveryservice.domain.exception.DeliveryServiceBusinessException;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Objects;
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

    @Builder.Default
    @Setter(AccessLevel.NONE)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 60)
    private ShipmentStatus status = ShipmentStatus.CREATED;

    @Column(name = "tracking_code", length = 50, unique = true)
    private String trackingCode;

    @Column(name = "delivered_at")
    private OffsetDateTime deliveredAt;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "canceled_at")
    private OffsetDateTime canceledAt;

    @Column(name = "cancellation_reason")
    private String cancellationReason;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private boolean deleted = false;

    @PrePersist
    private void prePersist() {
        this.code = UUID.randomUUID().toString();
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
        this.status = ShipmentStatus.CREATED;
        this.deleted = false;
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    private void setStatus(ShipmentStatus newStatus) {
        if (this.status.cantTransitionTo(newStatus)) {
            throw new DeliveryServiceBusinessException(
                    String.format("Não é permitido alterar o status de \"%s\" para \"%s\".",
                            this.status.getDescription(),
                            newStatus.getDescription())
            );
        }

        this.status = newStatus;
    }

    public void pickUp(String trackingCode) {
        this.setStatus(ShipmentStatus.PICKED_UP);
        this.trackingCode = trackingCode;
    }

    public void deliver(String receiverName) {
        this.setStatus(ShipmentStatus.DELIVERED);
        this.deliveredAt = OffsetDateTime.now();
        this.receiverName = receiverName;
    }

    public void cancel(String cancellationReason) {
        this.setStatus(ShipmentStatus.CANCELLED);
        this.canceledAt = OffsetDateTime.now();
        this.cancellationReason = cancellationReason;
    }

    public void deleteIt() {
        this.deleted = true;
    }

    public boolean cantBeUpdatedOrDeleted() {
        return Objects.nonNull(this.id) && !ShipmentStatus.CREATED.equals(this.status);
    }
}
