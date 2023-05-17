package com.example.asmtracebility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=ASMTraceabilityDb2;encrypt=true;trustServerCertificate=true;integratedSecurity=true";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection connection = DriverManager.getConnection(connectionString);
        return connection;
    }
}
