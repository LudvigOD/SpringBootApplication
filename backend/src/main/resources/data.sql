-- We can add SQL queries here, to have them run automatically on every startup.
-- Use this to insert test data.

INSERT INTO RACE (ID, TEAM_TOKEN)
VALUES
  (1, 'team-token-1'),
  (2, 'team-token-2');

INSERT INTO STATION (ID, RACE_ID, NAME)
VALUES
  (0, 1, 'Start'),
  (1, 1, 'LTH'),
  (2, 1, 'Mål');

INSERT INTO PARTICIPANT (RACE_ID, START_NBR, NAME)
VALUES
  (1, '002', 'Oo two'),
  (1, '012', 'Test tost'),
  (1, '003', 'Humpty Dumpty');

INSERT INTO TIME (RACE_ID, STATION_ID, START_NBR, TIME)
VALUES
  (1, 0, '001', '2025-01-27T10:10:13+00:00'),
  (1, 0, '012', '2025-01-27T10:01:54+00:00'),
  (1, 1, '012', '2025-01-27T10:26:42+00:00'),
  (1, 1, '012', '2025-01-28T10:26:40+00:00'),
  (1, 0, '003', '2025-01-27T11:07:50+00:00'),
  (1, 1, '003', '2025-01-27T11:29:24+00:00'),
  (1, 2, '003', '2025-01-27T11:45:53+00:00'),
  (1, 0, '004', '2025-01-27T12:00:46+00:00');
