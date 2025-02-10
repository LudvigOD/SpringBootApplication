-- We can add SQL queries here, to have them run automatically on every startup.
-- Use this to insert test data.

-- I think queries are case insensitive, so we could write:
-- insert into time (start_nbr, time) values ('01', '10:00:00') ...
INSERT INTO TIME (RACE_ID, STATION_ID, START_NBR, TIME)
VALUES
  (1, 1, '01', '2025-01-27T10:10:00+00:00'),
  (1, 1, '01', '2025-01-27T10:20:00+00:00'),
  (1, 1, '01', '2025-01-27T10:40:00+00:00'),
  (1, 1, '01', '2025-01-27T11:00:00+00:00'),
  (1, 1, '01', '2025-01-27T11:15:23+00:00'),
  (1, 1, '02', '2025-01-27T10:01:00+00:00'),
  (1, 1, '02', '2025-01-27T10:26:00+00:00'),
  (1, 1, '02', '2025-01-27T10:49:00+00:00'),
  (1, 1, '02', '2025-01-27T11:07:00+00:00'),
  (1, 1, '02', '2025-01-27T11:29:00+00:00')
;
