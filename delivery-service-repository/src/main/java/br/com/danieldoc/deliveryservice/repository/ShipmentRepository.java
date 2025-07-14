package br.com.danieldoc.deliveryservice.repository;

import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    @Query("from Shipment s where s.code = :code and s.deleted = false")
    Optional<Shipment> findByCodeAndNotDeleted(@Param("code") String code);

    @Query("select (s.id > 0) from Shipment s where s.code = :code and s.deleted = false")
    boolean existsByCodeAndNotDeleted(@Param("code") String code);
}
