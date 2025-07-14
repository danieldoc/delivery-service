package br.com.danieldoc.deliveryservice.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Getter
public enum ShipmentStatus {

    CREATED("Criado"),
    PICKED_UP("Coletado pelo transportador"),
    DELIVERED("Entregue"),
    CANCELLED("Cancelado");

    private final String description;

    private static final Map<ShipmentStatus, Set<ShipmentStatus>> NEXT =
            Map.of(
                    CREATED, EnumSet.of(PICKED_UP, CANCELLED),
                    PICKED_UP, EnumSet.of(DELIVERED, CANCELLED)
            );

    public boolean canTransitionTo(ShipmentStatus target) {
        return NEXT.getOrDefault(this, EnumSet.noneOf(ShipmentStatus.class)).contains(target);
    }

    public boolean cantTransitionTo(ShipmentStatus newStatus) {
        return !canTransitionTo(newStatus);
    }
}
