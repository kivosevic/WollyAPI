INSERT INTO user VALUES ('4961dc55-b2c3-413f-ba47-086dfbc4497a', '53060', 'email@email.com', 'firstName', 'lastName', '$2a$10$CAcWKXqqn/DdaV6wIkNnduhK6Y5.R5wLxpqFOO6FIwAh9hLDoSn5m', 'USER', null);

INSERT INTO wallet VALUES ('efa93ef3-4ebd-450b-97ac-0b0e68bb8660', '2761', null);

UPDATE user SET wallet_uuid = 'efa93ef3-4ebd-450b-97ac-0b0e68bb8660' WHERE uuid = '4961dc55-b2c3-413f-ba47-086dfbc4497a';
UPDATE wallet SET user_uuid = '4961dc55-b2c3-413f-ba47-086dfbc4497a' WHERE uuid = 'efa93ef3-4ebd-450b-97ac-0b0e68bb8660';
