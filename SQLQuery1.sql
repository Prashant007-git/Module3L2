drop database d1
create database d1
use d1
CREATE TABLE Table1 (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);
CREATE TABLE Table2 (
    id INT PRIMARY KEY,
    city VARCHAR(50) NOT NULL
);
INSERT INTO Table1 VALUES (1, 'prashant');
INSERT INTO Table1 VALUES (2, 'rohan');
INSERT INTO Table1 VALUES (3, 'aman');
INSERT INTO Table1 VALUES (4, 'amit');

INSERT INTO Table2 VALUES (1, 'New Delhi');
INSERT INTO Table2 VALUES (2, 'Bangalore');
INSERT INTO Table2 VALUES (5, 'Mohali');

-- cross join
SELECT * 
FROM Table1
CROSS JOIN Table2;

-- inner join
SELECT *
FROM Table1
INNER JOIN Table2 ON Table1.id = Table2.id;

-- left join
SELECT Table1.id, name, city
FROM Table1
LEFT JOIN Table2 ON Table1.id = Table2.id;

-- right join
SELECT Table1.id, name, city
FROM Table1
RIGHT JOIN Table2 ON Table1.id = Table2.id;

-- On
SELECT *
FROM Table1
INNER JOIN Table2 ON Table1.id = Table2.id;

-- Using
SELECT *
FROM Table1
INNER JOIN Table2 USING(id);

-- Create Table3
CREATE TABLE Table3 (
  t3_id INT PRIMARY KEY,
  t3_name VARCHAR(50),
  t3_age INT,
  t3_city VARCHAR(50)
);

-- Create Table4
CREATE TABLE Table4 (
  t4_id INT PRIMARY KEY,
  t4_country VARCHAR(50),
  t4_salary INT,
  t3_id INT FOREIGN KEY REFERENCES Table3(t3_id)
);

-- Insert data into Table3
INSERT INTO Table3 (t3_id, t3_name, t3_age, t3_city) VALUES (1, 'ankit', 25, 'kolkata');
INSERT INTO Table3 (t3_id, t3_name, t3_age, t3_city) VALUES (2, 'virat', 30, 'delhi');
INSERT INTO Table3 (t3_id, t3_name, t3_age, t3_city) VALUES (3, 'dhoni', 45, 'ranchi');

-- Insert data into Table4
INSERT INTO Table4 (t4_id, t4_country, t4_salary, t3_id) VALUES (1, 'india', 50000, 1);
INSERT INTO Table4 (t4_id, t4_country, t4_salary, t3_id) VALUES (2, 'england', 60000, 2);
INSERT INTO Table4 (t4_id, t4_country, t4_salary, t3_id) VALUES (3, 'scotland', 70000, 3);

-- Update a column in Table3
UPDATE Table3 SET t3_age = 27 WHERE t3_name = 'rohit';

-- Update a column in Table4
UPDATE Table4 SET t4_salary = 55000 WHERE t4_country = 'australia';

-- Create an index on Table3
CREATE INDEX idx_t3_name ON Table3 (t3_name);

-- Perform a cross join
SELECT *
FROM Table3
CROSS JOIN Table4;

-- Perform an inner join
SELECT *
FROM Table3
INNER JOIN Table4 ON Table3.t3_id = Table4.t3_id;

-- Perform a left outer join
SELECT *
FROM Table3
LEFT JOIN Table4 ON Table3.t3_id = Table4.t3_id;

-- Perform a right outer join
SELECT *
FROM Table3
RIGHT JOIN Table4 ON Table3.t3_id = Table4.t3_id;

-- Perform a full outer join
SELECT *
FROM Table3
FULL OUTER JOIN Table4 ON Table3.t3_id = Table4.t3_id;

--Active transactions
SELECT * FROM sys.dm_tran_active_transactions

--current running queries
SELECT *
FROM sys.dm_exec_requests
WHERE status = 'running'

