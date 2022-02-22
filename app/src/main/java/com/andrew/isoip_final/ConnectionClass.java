package com.andrew.isoip_final;


import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionClass {

    String address = "SSQL-12R2WEB01.client.parking.ru";
    String db = "andrewbo_mnshz";
    String login = "andrewbo_mnshz";
    String password = "8Brn3EyrI5";

    @SuppressLint("NewApi")
    public Connection CONN () {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;

        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnURL = "jdbc:jtds:sqlserver://" + address + ";" + "databaseName=" + db + ";user=" + login + ";password=" + password + ";";
            conn = DriverManager.getConnection(ConnURL);
        }  catch (ClassNotFoundException se) {
            Log.e("ERROR", se.getMessage());
        } catch (SQLException e) {
            Log.e("ERROR", e.getMessage());
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return conn;
    }
}
