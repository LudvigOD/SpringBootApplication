-- We can add SQL queries here, to have them run automatically on every startup.
-- Use this to insert test data.

-- I think queries are case insensitive, so we could write:
-- insert into time (start_nbr, time) values ('01', '10:00:00') ...
INSERT INTO TIME (RACE_ID, STATION_ID, START_NBR, TIME)
VALUES
  (1, 0, '001', '2025-01-27T10:10:00+00:00'),
  (1, 0, '012', '2025-01-27T10:01:00+00:00'),
  (1, 1, '012', '2025-01-27T10:26:00+00:00'),
  (1, 1, '012', '2025-01-28T10:26:00+00:00'),
  (1, 0, '003', '2025-01-27T11:07:00+00:00'),
  (1, 1, '003', '2025-01-27T11:29:00+00:00');

INSERT INTO PARTICIPANT (RACE_ID, START_NBR, NAME)
VALUES
  (1, '002', 'Oo two'),
  (1, '012', 'Test tost'),
  (1, '003', 'Humpty Dumpty');