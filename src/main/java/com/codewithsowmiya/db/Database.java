package com.codewithsowmiya.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private final static  String connectionUrl="jdbc:mysql://localhost:3306/TASKTRACKER?useSSL=false";
   private final  static String Username="root";
    private final  static String Password="root";


    public static Connection getConnection() throws SQLException {

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//extension from maven sql
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        connection = DriverManager.getConnection(connectionUrl, Username, Password);//call db
        System.out.println("connection" + !connection.isClosed());//close db
        return connection;

    }


}
