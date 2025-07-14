package br.com.danieldoc.deliveryservice.repository;

import br.com.danieldoc.deliveryservice.domain.entity.ShipmentStatusHistory;
import br.com.danieldoc.deliveryservice.repository.dto.ShipmentStatusHistoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShipmentStatusHistoryRepository extends JpaRepository<ShipmentStatusHistory, Long> {

    @Query("""
            SELECT new br.com.danieldoc.deliveryservice.repository.dto.ShipmentStatusHistoryDTO(
                ssh.status,
                ssh.createdAt
            )
            FROM ShipmentStatusHistory ssh
            INNER JOIN ssh.shipment s
            WHERE s.code = :shipmentCode
            """)
    List<ShipmentStatusHistoryDTO> findAllByShipmentCode(@Param("shipmentCode") String shipmentCode);
}
