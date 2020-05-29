package unk.test0;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector extends PGSimpleDataSource {
    @Override
    public Connection getConnection() throws SQLException {
        super.setDatabaseName(DBConfig.getDatabaseName());
        return super.getConnection(DBConfig.getUsr(),DBConfig.getPword());
    }

    @Override
    public String getDescription() {
        return "JOOQ DataSource";
    }

}
