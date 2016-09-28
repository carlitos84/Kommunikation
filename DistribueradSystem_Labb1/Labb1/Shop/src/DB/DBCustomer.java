package DB;

import BO.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Teddy on 2016-09-28.
 */
public class DBCustomer extends User{

    public static User getCustomerByLogin(String usrname, String pwd)
    {
        DBCustomer user = null;
        try{
            Connection con = DBManager.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM T_Customer WHERE K_Username = " + "\"" + usrname + "\"" + " and K_Password = " + "\"" + pwd + "\";");
            while(rs.next())
            {
                int id = rs.getInt("K_Id");
                String username = rs.getString("K_Username");
                String password = rs.getString("K_Password");
                user = new DBCustomer(id,username,password);
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return user;
    }

    private DBCustomer(int id, String username, String password) {
        super(id,username, password);
    }
}
