package com.example.asmtracebility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        String connectionString = "jdbc:sqlserver://localhost:57231;databaseName=ASMTraceabilityDb;user=asmadmin;password=ewbsmt;encrypt=true;trustServerCertificate=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection connection = DriverManager.getConnection(connectionString);
        return connection;
    }
}
