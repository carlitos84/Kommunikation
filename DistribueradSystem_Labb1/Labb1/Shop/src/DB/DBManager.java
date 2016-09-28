package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Teddy on 2016-09-28.
 */
public class DBManager {
    private String database, server, user, pwd;
    private static Connection con;

    public DBManager(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        database = "db_test";
        server = "jdbc:mysql://130.229.157.153:3306/" + database;
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




}
