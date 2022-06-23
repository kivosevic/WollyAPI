-- INSERT INTO cryptocurrency VALUES ('04767ea7-44e0-4333-879d-e127ee8678f6', 'btc', '1', 'btc', '1');
-- INSERT INTO cryptocurrency VALUES ('0b97e19a-78a1-4003-9972-3c359715fabc', 'eth', '22', 'eth', '23');

INSERT INTO user VALUES ('4961dc55-b2c3-413f-ba47-086dfbc4497a', '53060', 'email@email.com', 'firstName', 'lastName', '$2a$10$CAcWKXqqn/DdaV6wIkNnduhK6Y5.R5wLxpqFOO6FIwAh9hLDoSn5m', 'USER', null);

INSERT INTO wallet VALUES ('efa93ef3-4ebd-450b-97ac-0b0e68bb8660', '2761', null);

UPDATE user SET wallet_uuid = 'efa93ef3-4ebd-450b-97ac-0b0e68bb8660' WHERE uuid = '4961dc55-b2c3-413f-ba47-086dfbc4497a';
UPDATE wallet SET user_uuid = '4961dc55-b2c3-413f-ba47-086dfbc4497a' WHERE uuid = 'efa93ef3-4ebd-450b-97ac-0b0e68bb8660';

-- INSERT INTO wallet_item VALUES ('0b97e19a-78a1-4003-9972-3c359715fabc', '309', '0b97e19a-78a1-4003-9972-3c359715fabc', 'efa93ef3-4ebd-450b-97ac-0b0e68bb8660');
-- INSERT INTO wallet_item VALUES ('8d8d214d-b11b-4d7f-b6cd-3c4ece7c38ce', '919', '04767ea7-44e0-4333-879d-e127ee8678f6', 'efa93ef3-4ebd-450b-97ac-0b0e68bb8660');