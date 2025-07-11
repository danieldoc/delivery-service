package br.com.danieldoc.deliveryservice.restapi.api.v1.controller;

import br.com.danieldoc.deliveryservice.business.service.ShipmentService;
import br.com.danieldoc.deliveryservice.domain.entity.Shipment;
import br.com.danieldoc.deliveryservice.restapi.api.v1.mapper.ShipmentMapper;
import br.com.danieldoc.deliveryservice.restapi.api.v1.request.ShipmentRequest;
import br.com.danieldoc.deliveryservice.restapi.api.v1.response.ShipmentResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping("/api/v1/shipments")
@RestController
public class ShipmentController {

    private final ShipmentService shipmentService;
    private final ShipmentMapper shipmentMapper;

    public ShipmentController(ShipmentService shipmentService,
                              ShipmentMapper shipmentMapper) {
        this.shipmentService = shipmentService;
        this.shipmentMapper = shipmentMapper;
    }

    @GetMapping("/{code}")
    public ShipmentResponse getDetail(@NotBlank @PathVariable String code) {
        final Shipment shipment = shipmentService.getDetail(code);
        return shipmentMapper.toResponse(shipment);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<ShipmentResponse> create(@Valid @RequestBody ShipmentRequest shipmentRequest) {
        Shipment shipment = shipmentMapper.toShipment(shipmentRequest);
        shipment = shipmentService.save(shipment);

        final ShipmentResponse createdShipment = shipmentMapper.toResponse(shipment);

        final URI locationUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{code}")
                .buildAndExpand(createdShipment.getCode())
                .toUri();

        return ResponseEntity
                .created(locationUri)
                .body(createdShipment);
    }

    @PutMapping("/{code}")
    public ShipmentResponse update(@NotBlank @PathVariable String code,
                                   @Valid @RequestBody ShipmentRequest shipmentRequest) {
        Shipment shipment = shipmentService.getDetail(code);
        shipment = shipmentMapper.updateFields(shipmentRequest, shipment);
        shipment = shipmentService.save(shipment);
        return shipmentMapper.toResponse(shipment);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{code}")
    public void delete(@NotBlank @PathVariable String code) {
        shipmentService.deleteByCode(code);
    }
}
