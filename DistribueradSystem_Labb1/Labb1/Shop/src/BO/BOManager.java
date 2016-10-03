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

    public static boolean customerLogin(String username, String password)
    {

        if(User.getUser(username,password) == null)
        {
            return false;
        }
        return true;
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
