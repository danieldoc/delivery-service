package br.com.danieldoc.deliveryservice.restapi.api.v1;

import br.com.danieldoc.deliveryservice.restapi.api.v1.request.ShipmentRequest;
import br.com.danieldoc.deliveryservice.restapi.api.v1.response.ShipmentResponse;
import br.com.danieldoc.deliveryservice.restapi.exceptionhandler.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Shipment")
@RequestMapping("/v1/shipments")
public interface ShipmentApi {

    String SHIPMENT_REQUEST_EXAMPLE = """
            {
                "packageQuantity": 2,
                "deliveryDeadline": "2025-07-31T23:59:59-03:00",
                "customer": {
                    "document": "12345678900",
                    "fullName": "João da Silva",
                    "cellphone": null,
                    "email": null
                },
                "address": {
                    "street": "Avenida Paulista",
                    "number": "1578",
                    "neighborhood": "Bela Vista",
                    "complement": null,
                    "city": "São Paulo",
                    "state": "SP",
                    "postalCode": "01310200",
                    "referencePoint": null
                }
            }
            """;

    String SHIPMENT_RESPONSE_EXAMPLE = """
            {
              "code": "412de405-4e44-4398-b3d5-7c04a8eecaa9",
              "packageQuantity": 2,
              "deliveryDeadline": "2025-08-01T02:59:59Z",
              "customer": {
                "document": "12345678900",
                "fullName": "João da Silva",
                "cellphone": null,
                "email": null
              },
              "address": {
                "street": "Avenida Paulista",
                "number": "1578",
                "neighborhood": "Bela Vista",
                "complement": null,
                "city": "São Paulo",
                "state": "SP",
                "postalCode": "01310200",
                "referencePoint": null
              },
              "createdAt": "2025-07-11T01:01:50.911037-03:00",
              "updatedAt": "2025-07-11T01:01:50.911073-03:00"
            }
            """;

    String NOT_FOUND_RESPONSE_EXAMPLE = """
            {
                "code": "NOT_FOUND",
                "title": "Resource not found",
                "description": "Shipment with code 862d255e-efae-4404-b894-135c054542f not found",
                "fields": null
            }
            """;

    String SERVER_ERROR_RESPONSE_EXAMPLE = """
            {
                "code": "SERVER_ERROR",
                "title": "Server error",
                "description": "Ocorreu um erro interno inesperado no sistema. Tente novamente e, se o problema persistir, entre em contato com o administrador do sistema..",
                "fields": null
            }
            """;

    @Operation(summary = "Get a shipment by code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shipment found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = SHIPMENT_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "Shipment not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "500", description = "Internal server error was thrown", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = SERVER_ERROR_RESPONSE_EXAMPLE))),
    })
    @GetMapping("/{code}")
    ShipmentResponse getDetail(@Parameter(description = "Shipment Identifier", example = "862d255e-efae-4404-b894-135c054542ff") @NotBlank @PathVariable String code);

    @Operation(summary = "Create a new shipment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = SHIPMENT_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Validation error occurred", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error was thrown", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = SERVER_ERROR_RESPONSE_EXAMPLE))),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ResponseEntity<ShipmentResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Shipment to create", required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ShipmentRequest.class),
                            examples = @ExampleObject(SHIPMENT_REQUEST_EXAMPLE)))
            @Valid @RequestBody ShipmentRequest shipmentRequest);

    @Operation(summary = "Update a shipment by code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = SHIPMENT_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Validation error occurred", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Shipment not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "500", description = "Internal server error was thrown", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = SERVER_ERROR_RESPONSE_EXAMPLE))),
    })
    @PutMapping("/{code}")
    ShipmentResponse update(@Parameter(description = "Shipment Identifier", example = "862d255e-efae-4404-b894-135c054542ff")
                            @NotBlank @PathVariable String code,

                            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Shipment to update", required = true,
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(example = SHIPMENT_REQUEST_EXAMPLE)))
                            @Valid @RequestBody ShipmentRequest shipmentRequest);

    @Operation(summary = "Delete a shipment by code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Shipment not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "500", description = "Internal server error was thrown", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = SERVER_ERROR_RESPONSE_EXAMPLE))),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{code}")
    void delete(@Parameter(description = "Shipment Identifier", example = "862d255e-efae-4404-b894-135c054542ff")
                @NotBlank @PathVariable String code);
}
