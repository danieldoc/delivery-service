package br.com.danieldoc.deliveryservice.restapi.api.v1.controller;

import br.com.danieldoc.deliveryservice.business.service.ShipmentService;
import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.restapi.api.v1.ShipmentApi;
import br.com.danieldoc.deliveryservice.restapi.api.v1.mapper.ShipmentMapper;
import br.com.danieldoc.deliveryservice.restapi.api.v1.request.ShipmentRequest;
import br.com.danieldoc.deliveryservice.restapi.api.v1.response.ShipmentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
public class ShipmentController implements ShipmentApi {

    private final ShipmentService shipmentService;
    private final ShipmentMapper shipmentMapper;

    public ShipmentController(ShipmentService shipmentService,
                              ShipmentMapper shipmentMapper) {
        this.shipmentService = shipmentService;
        this.shipmentMapper = shipmentMapper;
    }

    @Override
    public ShipmentResponse getDetail(String code) {
        log.info("Start getDetail with code={}", code);

        final Shipment shipment = shipmentService.getDetail(code);
        final ShipmentResponse response = shipmentMapper.toResponse(shipment);

        log.info("End getDetail with code={}", code);
        return response;
    }

    @Override
    public ResponseEntity<ShipmentResponse> create(ShipmentRequest shipmentRequest) {
        log.info("Start create shipment");

        Shipment shipment = shipmentMapper.toShipment(shipmentRequest);
        shipment = shipmentService.save(shipment);

        final ShipmentResponse createdShipment = shipmentMapper.toResponse(shipment);

        final URI locationUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{code}")
                .buildAndExpand(createdShipment.getCode())
                .toUri();

        log.info("End create shipment with code={}", createdShipment.getCode());
        return ResponseEntity
                .created(locationUri)
                .body(createdShipment);
    }

    @Override
    public ShipmentResponse update(String code, ShipmentRequest shipmentRequest) {
        log.info("Start update shipment with code={}", code);

        Shipment shipment = shipmentService.getDetail(code);
        shipment = shipmentMapper.updateFields(shipmentRequest, shipment);
        shipment = shipmentService.save(shipment);
        final ShipmentResponse response = shipmentMapper.toResponse(shipment);

        log.info("End update shipment with code={}", code);
        return response;
    }

    @Override
    public void delete(String code) {
        log.info("Start delete with code={}", code);

        shipmentService.deleteByCode(code);

        log.info("End delete with code={}", code);
    }
}
