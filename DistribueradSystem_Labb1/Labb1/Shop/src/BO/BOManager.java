package BO;

import DB.DBManager;

import java.util.Hashtable;

/**
 * Created by Teddy on 2016-09-28.
 */
public class BOManager {
    private Order shopCart;

    public BOManager()
    {
        //shopCart = new Order();
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

    public Hashtable getShoppingCart()
    {
        return shopCart.lookCart();
    }

    public void addToShoppingCart(int itemId)
    {
        Item item = Item.searchItemsById(itemId);
        shopCart.addItem(item);
    }

}
