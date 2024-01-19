package com.example.qlnn1;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
public class LUULY1 {
  //  public static void main(String[] args) {

    public LUULY1() {
    }
       public static Connection getConnection() {
        Connection databaselink = null;
           String databaseName = "master";
           String databaseUser = "sa";
           String databasePassword = "123";
           String ulr = "jdbc:sqlserver://localhost:1433;databaseName=master;user=sa;password=123;encrypt=false";
           setEncrypt(false);
           try {
               Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
              databaselink = DriverManager.getConnection(ulr,databaseUser,databasePassword);
               System.out.println("1");
           } catch (Exception e) {
               e.printStackTrace();
           }
return databaselink;
       }
    private static void setEncrypt(boolean b) {
    }
}

