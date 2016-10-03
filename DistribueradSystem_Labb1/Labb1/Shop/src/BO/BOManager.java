package BO;

import Controller.ShoppingCart;
import DB.DBManager;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Teddy on 2016-09-28.
 */
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



}
