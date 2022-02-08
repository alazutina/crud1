package com.anna.crud.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import com.anna.crud.model.Tag;
import com.anna.crud.repository.TagRepository;
import java.util.ArrayList;
import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.*;
import com.anna.crud.util.SQLQuery;

//Singleton
public class JdbcUtils {

    //TODO: read properties from the file
    private Properties properties;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/test";

    /**
     * User and Password
     */
    static final String USER = "test";
    static final String PASSWORD = "123456";

    private static Connection connection;

    public static void setConnection(){
        try{
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
    } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        //    return null;
        }
    }

    public static PreparedStatement getPreparedStatement(String sql)  {
           try {
              // Class.forName("com.mysql.jdbc.Driver");
              // connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
               return connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
//           catch (ClassNotFoundException e){
//               e.printStackTrace();
//               return null;
//           }
    }
}
