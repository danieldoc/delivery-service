CREATE TABLE shipment_status_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    status VARCHAR(20) NOT NULL,
    shipment_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_shipment
        FOREIGN KEY (shipment_id)
        REFERENCES shipment(id)
);