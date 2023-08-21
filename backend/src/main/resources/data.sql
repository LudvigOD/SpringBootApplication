-- We can add SQL queries here, to have them run automatically on every startup.
-- Use this to insert test data.

-- I think queries are case insensitive, so we could write:
-- insert into time (start_nbr, time) values ('01', '10:00:00') ...
INSERT INTO TIME (START_NBR, TIME) 
VALUES
  ('01', '10:00:00'),
  ('01', '10:20:00'),
  ('01', '10:40:00'),
  ('01', '11:00:00'),
  ('01', '11:15:23'),
  ('02', '10:01:00'),
  ('02', '10:26:00'),
  ('02', '10:49:00'),
  ('02', '11:07:00'),
  ('02', '11:29:00')
;