package com.rh.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        String railwayHost = System.getenv("MYSQLHOST");

        if (railwayHost != null && !railwayHost.isEmpty()) {
            // Environnement Railway (en ligne)
            String host = System.getenv("MYSQLHOST");
            String port = System.getenv("MYSQLPORT");
            String database = System.getenv("MYSQL_DATABASE");
            String user = System.getenv("MYSQLUSER");
            String password = System.getenv("MYSQLPASSWORD");
            URL = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&allowPublicKeyRetrieval=true";
            USER = user;
            PASSWORD = password;
        } else {
            // Environnement local
            URL = "jdbc:mysql://localhost:3306/gestion_rh?useSSL=false";
            USER = "root";
            PASSWORD = "";
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}