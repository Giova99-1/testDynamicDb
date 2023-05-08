package org.example;

import java.sql.*;
import java.util.List;

public class DbManager {

    public String dbConnectionString = "jdbc:postgresql://localhost/food_storage";
    private String usr ="pi";
    private String pass ="raspberry";

    private Connection connection;

    public DbManager(Connection connection) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        this.connection = connection;
    }

    public DbManager() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        this.connection = DriverManager.getConnection(dbConnectionString, usr, pass);
    }

    public DbManager(String dbConnectionString) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        this.dbConnectionString = dbConnectionString;
        this.connection = DriverManager.getConnection(this.dbConnectionString, usr, pass);
    }

    public List<Expire> getExpire() throws SQLException {
        ResultSet rs= executeQuery("SELECT * FROM Expire");
        ResultSetMapper<Expire> rsMap = new ResultSetMapper<Expire>();
        return rsMap.mapRersultSetToObject(rs, Expire.class);
    }

    private ResultSet executeQuery(String query) throws SQLException {
        try {
            Statement statement =  connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
    }
}
