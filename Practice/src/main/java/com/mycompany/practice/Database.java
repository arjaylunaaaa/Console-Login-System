package com.mycompany.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/mydb?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection connection(){
        try{
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return null;
    }

}
