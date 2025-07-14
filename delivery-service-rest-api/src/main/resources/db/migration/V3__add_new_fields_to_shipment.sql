ALTER TABLE shipment ADD status varchar(60) NOT NULL;
ALTER TABLE shipment ADD tracking_code varchar(60) NULL;
ALTER TABLE shipment ADD delivered_at TIMESTAMP NULL;
ALTER TABLE shipment ADD receiver_name varchar(255) NULL;
ALTER TABLE shipment ADD canceled_at TIMESTAMP NULL;
ALTER TABLE shipment ADD cancellation_reason varchar(255) NULL;
ALTER TABLE shipment DROP COLUMN version;