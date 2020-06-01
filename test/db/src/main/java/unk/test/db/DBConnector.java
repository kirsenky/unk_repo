package unk.test.db;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector extends PGSimpleDataSource {
    private DBConfig config=DBConfig.getConfig();

    @Override
    public Connection getConnection() throws SQLException {
        super.setDatabaseName(config.getDatabaseName());
        return super.getConnection(config.getUsr(),config.getPword());
    }

    @Override
    public String getDescription() {
        return "JOOQ DataSource";
    }

}
