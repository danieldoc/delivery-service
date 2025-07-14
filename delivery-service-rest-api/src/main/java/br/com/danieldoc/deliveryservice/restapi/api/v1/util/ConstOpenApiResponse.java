package br.com.danieldoc.deliveryservice.restapi.api.v1.util;

public final class ConstOpenApiResponse {

    private ConstOpenApiResponse() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static final class Common {

        private Common() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }

        public static final String NOT_FOUND_RESPONSE_EXAMPLE = """
                {
                    "code": "NOT_FOUND",
                    "title": "Resource not found",
                    "description": "Shipment with code 862d255e-efae-4404-b894-135c054542f not found",
                    "fields": null
                }
                """;

        public static final String SERVER_ERROR_RESPONSE_EXAMPLE = """
                {
                    "code": "SERVER_ERROR",
                    "title": "Server error",
                    "description": "Ocorreu um erro interno inesperado no sistema. Tente novamente e, se o problema persistir, entre em contato com o administrador do sistema..",
                    "fields": null
                }
                """;
    }

    public static final class ShipmentApi {
        private ShipmentApi() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }

        public static final String SHIPMENT_REQUEST_EXAMPLE = """
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
                        "zipCode": "01310200",
                        "referencePoint": null
                    }
                }
                """;

        public static final String SHIPMENT_RESPONSE_EXAMPLE = """
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
                    "zipCode": "01310200",
                    "referencePoint": null
                  },
                  "createdAt": "2025-07-11T01:01:50.911037-03:00",
                  "updatedAt": "2025-07-11T01:01:50.911073-03:00"
                }
                """;
    }

    public static final class ShipmentStatusApi {

        private ShipmentStatusApi() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }

        public static final String LIST_SHIPMENT_STATUS_HISTORY_EXAMPLE = """
                [
                    {
                        "status": "CREATED",
                        "createdAt": "2025-07-14T04:10:05Z"
                    },
                    {
                        "status": "PICKED_UP",
                        "createdAt": "2025-07-14T04:10:46Z"
                    },
                    {
                        "status": "DELIVERED",
                        "createdAt": "2025-07-14T04:11:18Z"
                    }
                ]
                """;

        public static final String SHIPMENT_PICKUP_REQUEST_EXAMPLE = """
                {
                    "trackingCode": "AA123456789BR"
                }
                """;

        public static final String SHIPMENT_DELIVERY_REQUEST_EXAMPLE = """
                {
                    "receiverName": "Maria da Silva"
                }
                """;

        public static final String SHIPMENT_CANCELLATION_REQUEST_EXAMPLE = """
                {
                    "reason": "Cliente solicitou cancelamento"
                }
                """;
    }
}
