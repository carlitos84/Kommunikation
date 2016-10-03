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
        if(usrname == null || pwd == null)
        {
            return null;
        }

        try{
            Connection con = DBManager.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM T_User WHERE K_Username = " + "\"" + usrname + "\"" + " and K_Password = " + "\"" + pwd + "\";");
            while(rs.next())
            {
                int id = rs.getInt("K_Id");
                String username = rs.getString("K_Username");
                String password = rs.getString("K_Password");
                boolean customer = rs.getBoolean("K_Customer");
                boolean staff = rs.getBoolean("K_Staff");
                boolean admin = rs.getBoolean("K_Admin");
                user = new DBCustomer(id,username,password, customer, staff, admin);
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return user;
    }

    private DBCustomer(int id, String username, String password, boolean customer, boolean staff, boolean admin) {
        super(id, username, password, customer, staff, admin);
    }
}
