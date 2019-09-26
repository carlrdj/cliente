/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author carlr
 */
public class ConnectDB {
    private final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private final String url = "jdbc:sqlserver://192.168.79.1:1433;database=db_app;user=carl;password=carl;";

    public Connection getConnection() throws SQLException {
        Connection cn = null;
        try {
            Class.forName(driver).newInstance();
            cn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException
                | IllegalAccessException
                | InstantiationException e) {
            // No encuentra clase
            // Accesos ilegales
            // Instacia al driver del SQL .jar
            throw new SQLException(e.getMessage());
        }
        return cn;
    }
}
