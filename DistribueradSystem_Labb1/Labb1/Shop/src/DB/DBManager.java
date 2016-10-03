package DB;

import java.sql.*;
import java.util.ArrayList;
import BO.Item;
import BO.Order;
import BO.User;

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
        server = "jdbc:mysql://130.229.152.124:3306/" + database;
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

    public static int makeOrder(Order order)
    {
        User user = DBCustomer.getUser(order.getUsername(), order.getPassword());

        if(user == null || !user.isCustomer())
        {
            return 100;
        }

        try {
            Connection con = getCon();
            Statement st = con.createStatement();
            st.execute("START TRANSACTION;");
            st.execute("insert into T_Order (K_CustomerId) VALUES("+ user.getId() +");");
            ResultSet rs = st.executeQuery("SELECT K_Id FROM T_Order WHERE K_CustomerId =" + user.getId() + " ORDER BY K_Id DESC;");
            rs.next();
            int orderId = rs.getInt("K_Id");
            for(int i=0; i<order.getSize();i++)
            {
                int stockStatus = checkStockStatus(order.getItemIdFrom(i),order.getItemAmountToBuyFrom(i));
                int newStockStatus = stockStatus - order.getItemAmountToBuyFrom(i);
                if(newStockStatus>= 0)
                {
                    st.execute("UPDATE T_Item SET K_Quantity = "+ newStockStatus +" WHERE K_Id = " + order.getItemIdFrom(i) + ";");
                    st.execute("insert into T_OrderItems (K_OrderId, K_ItemId, K_Quantity) VALUES("+ orderId+","+ order.getItemIdFrom(i)+","+ order.getItemAmountToBuyFrom(i) +");");
                }
                else
                {
                    System.out.println("Rollback");
                    st.execute("ROLLBACK ;");
                    return 100;
                }
            }
            st.execute("COMMIT;");
            return 200;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 100;
    }

    private static int checkStockStatus(int itemId, int amount)
    {
        try {
            Connection con = getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT K_Quantity FROM T_Item WHERE K_Id =" + itemId);
            if(rs.next())
            {
                return rs.getInt("K_Quantity");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
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
