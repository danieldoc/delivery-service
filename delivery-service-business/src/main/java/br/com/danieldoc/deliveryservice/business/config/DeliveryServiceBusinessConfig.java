package br.com.danieldoc.deliveryservice.business.config;

import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.repository.ShipmentRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackageClasses = ShipmentRepository.class)
@EntityScan(basePackageClasses = Shipment.class)
@Configuration
public class DeliveryServiceBusinessConfig {
}
