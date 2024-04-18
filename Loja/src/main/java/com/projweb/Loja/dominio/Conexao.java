package com.projweb.Loja.dominio;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection getConnection() throws SQLException, URISyntaxException {
        String dbUri = "localhost";
        String dbPort = "5432";
        String dbName = "trabpw";

        String username = "postgres";
        String password = "171210";
        String dbUrl = "jdbc:postgresql://" + dbUri + ':' + dbPort + "/" + dbName + "?serverTimezone=UTC";

        System.out.println(dbUrl);

        return DriverManager.getConnection(dbUrl, username, password);
    }
}