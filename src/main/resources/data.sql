-- delete from BOOKING;
-- delete from ITEMS;
-- delete from USERS;
-- delete from ITEM_REQUEST;

ALTER TABLE COMMENTS ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE BOOKING ALTER COLUMN booking_id RESTART WITH 1;
ALTER TABLE USERS ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE ITEMS ALTER COLUMN item_id RESTART WITH 1;
ALTER TABLE ITEM_REQUEST ALTER COLUMN request_id RESTART WITH 1;

