/*psql.exe -U postgres -d collector_db -f C:\test\scripts\test.sql*/
CREATE ROLE test WITH LOGIN NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION password 'assa';
grant all on schema public to test;
CREATE DATABASE collector_db;
grant all on collector_db to test;


