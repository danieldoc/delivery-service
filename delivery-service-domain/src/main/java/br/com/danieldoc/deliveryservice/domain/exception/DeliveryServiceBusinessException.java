package br.com.danieldoc.deliveryservice.domain.exception;

public class DeliveryServiceBusinessException extends RuntimeException {

    public DeliveryServiceBusinessException(String message) {
        super(message);
    }

    public DeliveryServiceBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeliveryServiceBusinessException(Throwable cause) {
        super(cause);
    }
}
