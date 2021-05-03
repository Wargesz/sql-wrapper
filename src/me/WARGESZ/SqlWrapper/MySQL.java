package me.WARGESZ.SqlWrapper;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.sql.*;
import java.util.Arrays;

public class MySQL {
    private String host = "localhost";
    private String port = "3306";
    private String database = "wrapper";
    private String username = "root";
    private String password = "";
    private Connection connection;

    public boolean isConnected() {
        return (connection == null ? false : true);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database,username,password);
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
                psString = "CREATE TABLE IF NOT EXISTS " + args[1] + " (" + args[2] + ")";
                //System.out.println(psString);
                execute(psString,0,"");
            case "select" :
                if (args.length>4) {
                    psString = "SELECT " + args[1] + " FROM " + args[2]+" WHERE "+ args[3]+"=?";
                    execute(psString,1,args[4]);
                } else {
                    psString = "SELECT " + args[1] + " FROM " + args[2];
                    execute(psString,1,"");
                }
        }

    }
    public String execute(String stmnt, Integer id,String var) {
        if (id == 0) {
            try {
                PreparedStatement ps = getConnection().prepareStatement(stmnt);
                ps.executeUpdate();
                if (id == 0) {
                    System.out.println("CREATE SUCCESS");
                }
            } catch (SQLException e) {
                System.out.println("CREATE FAILED");
                e.printStackTrace();
            }
        }
        if (id == 1) {
            try {
                PreparedStatement ps = getConnection().prepareStatement(stmnt);
                if (stmnt.contains("WHERE")) {
                    ps.setString(1, var);
                }
                ResultSet results = ps.executeQuery();
                if (results.next()) {
                    System.out.println(results.getString(1));
                }
            } catch (SQLException e) {
                System.out.println("SELECT FAILED");
                e.printStackTrace();
            }
        }
        return null;
    }
}