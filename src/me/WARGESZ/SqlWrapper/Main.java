package me.WARGESZ.SqlWrapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws Exception{
        MySQL sql = new MySQL();
        try {
            sql.connect();
            System.out.println("Database connected.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database not connected.");
        }
        //sql.wrapper("create;players;NAME VARCHAR(100),UUID VARCHAR(100),PRIMARY KEY (NAME)"); create
        sql.wrapper("select;*;players;");
    }
}
