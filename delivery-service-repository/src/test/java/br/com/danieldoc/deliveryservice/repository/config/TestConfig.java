package br.com.danieldoc.deliveryservice.repository.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "br.com.danieldoc.deliveryservice.repository")
@EntityScan(basePackages = "br.com.danieldoc.deliveryservice.domain.entity")
@Configuration
public class TestConfig {
}
