package DB;

import BO.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Teddy on 2016-10-04.
 */
public class DBOrder {

    public static ArrayList<int[]> getAllOrders()
    {
        String query = "SELECT * FROM T_Order";
        return getAllOrderswithQuery(query);
    }

    public static Order getItemInOrderByOrderId(int orderId)
    {
        String query = "SELECT T_Order.K_Id, T_Order.K_CustomerId , T_OrderItems.K_ItemId, T_OrderItems.K_Quantity " +
                "FROM T_Order " +
                "inner join T_OrderItems " +
                "on T_Order.K_Id = T_OrderItems.K_OrderId and T_OrderItems.K_OrderId = " + orderId;
        return  getAllOrderByOrderIdWithQuery(query);
    }

    private static ArrayList<int[]> getAllOrderswithQuery(String query)
    {
        ArrayList<int[]> itemList = new ArrayList<>();
        try{
            ResultSet rs = DBManager.getOrdersByQuery(query);
            while(rs.next())
            {
                int orderId = rs.getInt("K_Id");
                int customerId = rs.getInt("K_CustomerId");
                int[] orderIdAndCustomerId = {orderId, customerId};
                itemList.add(orderIdAndCustomerId);
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return itemList;
    }

    private static Order getAllOrderByOrderIdWithQuery(String query)
    {
        Order itemList = new Order();
        try{
            ResultSet rs = DBManager.getOrdersByQuery(query);
            while(rs.next())
            {
                int orderId = rs.getInt("K_Id");
                int itemId = rs.getInt("K_ItemId");
                int quantity = rs.getInt("K_Quantity");
                int customerId = rs.getInt("K_CustomerId");
                itemList.addItem(itemId, quantity, customerId, orderId);
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return itemList;
    }
}
