package DB;

import java.sql.*;
import java.util.ArrayList;
import BO.Item;

/**
 * Created by Teddy on 2016-09-28.
 */
//Singelton
public class DBManager {
    private String database, server, user, pwd;
    private static Connection con;
    static boolean alredyInitialized = false;

    public static void init()
    {
        if(!alredyInitialized)
        {
            DBManager dbmanager = new DBManager();
            alredyInitialized = true;
            System.out.print("new page");
        }
    }
    private DBManager(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        database = "db_test";
        server = "jdbc:mysql://localhost:3306/" + database;
        user = "customer";
        pwd = "customer";
        try{
            con = DriverManager.getConnection(server, user, pwd);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            try {
                con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static Connection getCon()
    {
        return con;
    }

    public static ResultSet getItemsByQuery(String query)
    {
        ResultSet rs = null;
        try{

            Statement st = con.createStatement();
            rs = st.executeQuery(query);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return rs;
    }
}
