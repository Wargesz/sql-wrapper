package me.WARGESZ.SqlWrapper;

import sun.java2d.pipe.AAShapePipe;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MySQL {
    String host = "localhost";
    String port = "3306";
    String database = "wrapper";
    String username = "root";
    String password = "";
    Connection connection;

    public boolean isConnected() {
        return (connection == null ? false : true);
    }

    public void connect(){
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                System.out.println("Database connected.");
            } catch (SQLException e) {
                System.out.println("Database not connected");
            }
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void wrapper(String command) {
        String[] args = command.split(";");
        String psString;
        switch (args[0]) {
            case "create" :
                //psString = "CREATE TABLE IF NOT EXISTS " + args[1] + " (" + args[2] + ")";
                psString = "CREATE TABLE IF NOT EXISTS " + args[1] + "(";
                for (int i = 1;i <=args.length-2;i++) {
                    if (i == args.length-2 ) {
                        psString +=" "+ args[i+2-1] + ")";

                    } else {
                        psString += " " + args[i + 2 - 1] + ",";
                    }
                }
                execute(psString,0);
                return;
            case "select" :
                if (args.length>5) {
                    psString = "SELECT " + args[1] + " FROM " + args[2]+" WHERE "+ args[3]+"=" + args[4];
                    execute(psString,1);
                } else {
                    psString = "SELECT " + args[1] + " FROM " + args[2];
                    execute(psString,1);
                }
                return;
            case "insert":
                psString = "INSERT INTO " + args[1] + " VALUES (";
                int varcount = args.length-2;
                for (int i = 1;i <= varcount;i++) {
                    if (i == varcount) {
                        psString+="'"+args[i+1] + "')";
                        break;
                    }
                    psString+="'"+args[i+1] + "',";
                }
                execute(psString,2);
                return;
            case "delete":
                if (args.length==4) {
                    psString = "DELETE FROM " + args[1] + " WHERE " + args[2] + " = '" + args[3] +"'";
                    execute(psString,3);
                }
                if (args.length==2) {
                    psString = "DELETE FROM " + args[1];
                    execute(psString,3);
                }
                if (args.length==3) {
                    System.out.println("SYTAX HIBA");
                    return;
                }
                return;
            case "update":
                int length = (args.length-2)/2;
                psString = "UPDATE " + args[1] + " SET ";
                int count = 0;
                for (int i = 1;i<=length*2;i=i+2) {
                    count++;
                    if (count==length) {
                        psString+= "WHERE " + args[i+1] + " = '" + args[i+2] + "'";
                        break;
                    }
                    if (count==length-1) {
                        psString+=args[i+1] + " = '" + args[i+2] + "' ";
                    } else {
                        psString += args[i + 1] + " = '" + args[i + 2] + "', ";
                    }
                }
                execute(psString,4);
                return;
        }

    }

    public void execute(String stmnt, Integer id) {
        switch (id) {
            case 0 :
                //create
                try {
                    PreparedStatement ps = getConnection().prepareStatement(stmnt);
                    ps.executeUpdate();
                    System.out.println("CREATE SUCCESS");
                    return;
                } catch (SQLException e) {
                    System.out.println("CREATE FAILED");
                    e.printStackTrace();
                }
                return;
            case 1 :
                //select
                try {
                    PreparedStatement ps = getConnection().prepareStatement(stmnt);
                    ResultSet results = ps.executeQuery();
                    ResultSetMetaData metaData = ps.getMetaData();
                    List<List<String>> list = new ArrayList<>();
                    while (results.next()==true) {
                        List<String> values = new ArrayList<>();
                        for (int i = 1; i <= metaData.getColumnCount(); i++) {
                            values.add(results.getString(i));
                        }
                        list.add(values);
                    }
                    System.out.println(list);
                    System.out.println("SELECT SUCCESSFUL");
                    return;
                } catch (SQLException e) {
                    System.out.println("SELECT FAILED");
                    e.printStackTrace();
                }
                return;
            case 2 :
                //insert
                try {
                    PreparedStatement ps = getConnection().prepareStatement(stmnt);
                    ps.executeUpdate();
                    System.out.println("INSERT SUCCESSFUL");
                } catch (SQLException e) {
                    System.out.println("INSERT FAILED");
                    e.printStackTrace();
                }
                return;
            case 3 :
                //delete
                try {
                    PreparedStatement ps = getConnection().prepareStatement(stmnt);
                    ps.executeUpdate();
                    System.out.println("DELETE SUCCESSFUL");
                } catch (SQLException e) {
                    System.out.println("DELETE FAILED");
                    e.printStackTrace();
                }
                return;
            case 4 :
                //update
                try {
                    PreparedStatement ps = getConnection().prepareStatement(stmnt);
                    ps.executeUpdate();
                    System.out.println("UPDATE SUCCESSFUL");
                } catch (SQLException e) {
                    System.out.println("UPDATE FAILED");
                    e.printStackTrace();
                }
                return;
        }
    }
}