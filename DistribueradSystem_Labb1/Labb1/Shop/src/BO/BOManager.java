package BO;

import Controller.ShoppingCart;
import DB.DBManager;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Teddy on 2016-09-28.
 */

/**
 * Bomanager takes care of the the different functionalties a user can do and also provides the user with resources.
 * **/
public class BOManager {

    public BOManager()
    {

    }

    public static void init()
    {
        DBManager.init();
    }

    public Hashtable searchByManufactor(String manufactor)
    {
        LookItems look = new LookItems();
        return look.getItemsWithManufactor(manufactor);
    }

    public Hashtable searchByModel(String model)
    {
        LookItems look = new LookItems();
        return look.getItemsWithModel(model);
    }

    public static boolean userLogin(String username, String password, String usertype)
    {
        User user = User.getUser(username,password);

        if(user  == null)
        {
            return false;
        }

        switch (usertype)
        {
            case "customer":
                return user.isCustomer();
            case "staff":
                return user.isStaff();
            case "admin":
                return user.isAdmin();
            default:
                System.out.println("int BOManager-userLogin deafault!!! "+usertype);
        }
        return false;
    }

    public static int makeOrder(ShoppingCart cart, String username, String password)
    {
        Order orderList = new Order();
        orderList.setUsername(username);
        orderList.setPassword(password);
        for (int i=0; i<cart.getSize();i++)
        {
            ShoppingCart.ItemDTO item = cart.getItem(i);
            orderList.addItem(item.getId(), item.getQtyInShoppingCart());
        }
        return DBManager.makeOrder(orderList);
    }
    //staff member only:
    public static Hashtable getAllOrders()
    {
        LookOrders look = new LookOrders();
        return look.getAllOrders();
    }

    public static Hashtable getItemsInOrderByOrderId(int orderId)
    {
        LookOrders look = new LookOrders();
        return look.getOrderByOrderId(orderId);
    }

    public static int packetOrderByStaffWithOrderId(int orderId, String username, String password)
    {

        return DBManager.packetOrderByStaff(orderId, username, password);
    }


}
