-- STATUS MASTER
INSERT IGNORE INTO status(status_id, status_name)
VALUES (1, 'PENDING');

INSERT IGNORE INTO status(status_id, status_name)
VALUES (2, 'PROCESSING');

INSERT IGNORE INTO status(status_id, status_name)
VALUES (3, 'COMPLETED');


-- PAYMENT MASTER
INSERT IGNORE INTO payment_status(p_id, p_name)
VALUES (1, 'PAID');

INSERT IGNORE INTO payment_status (p_id, p_name)
VALUES (2, 'UNPAID');


-- PAYMENT MODE MASTER
INSERT IGNORE INTO payment_mode (id, mode)
VALUES (1, 'GPAY');

INSERT IGNORE INTO payment_mode (id, mode)
VALUES (2, 'CASH');