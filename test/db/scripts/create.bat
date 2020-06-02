@rem
@rem         PLEASE UPDATE TWO LINES BELOW ACCORDING TO YOUR LOCAL PATHES
@rem
set psql_path=D:\DB\PostgreSQL\12\bin\
set sql_path=D:\unk_repo\test\scripts\

%psql_path%psql.exe -U postgres -f %sql_path%postgres_create1.sql
%psql_path%psql.exe -U postgres -d collector_db -f %sql_path%postgres_create2.sql
%psql_path%psql.exe -U test -d collector_db -f %sql_path%test.sql