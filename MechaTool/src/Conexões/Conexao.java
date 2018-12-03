/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexões;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class Conexao {

    static {
        try {
            //Carrega o driver do banco
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver do banco não encontrado.");
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
         
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/projeto",
                    "root",
                    ""
                    );
        } catch (SQLException ex) {
            System.err.print("Erro ao obter conexao: " + ex.getMessage());
        }
        return conn;
    }

}

