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
        Integer i = 3;
        if (sql.isConnected()) {
            //sql.wrapper("create;playerlist; ID INT,NAME VARCHAR(100),UUID VARCHAR(100),PRIMARY KEY (ID)"); //old create
            //sql.wrapper("create;playerbase;ID INT;NAME VARCHAR(100);UUID VARCHAR(100);PRIMARY KEY (ID)"); //create
            //sql.wrapper("select;*;players;"); //select
            //sql.wrapper("insert;playerbase;"+i+";antal;200"); //insert
            //sql.wrapper("delete;playerlist;name;tibike;"); //delete row
            //sql.wrapper("delete;playerlist;"); //delete all
            //sql.wrapper("update;playerlist;name;xerxész;uuid;6969;id;3"); //update
        }
    }
}
