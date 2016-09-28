package BO;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by Teddy on 2016-09-28.
 */
public class ShoppingCart {
    private ArrayList<Item> shoppingCart;

    public ShoppingCart()
    {
        shoppingCart = new ArrayList<>();
    }

    public void addItem(Item item)
    {
        shoppingCart.add(item);
    }

    public boolean removeItem(Item item)
    {
        return shoppingCart.remove(item);
    }

    public Hashtable lookCart()
    {
        Hashtable table = new Hashtable();
        table.put("size", shoppingCart.size());
        Iterator ite = shoppingCart.iterator();
        while (ite.hasNext())
        {
            Hashtable item = new Hashtable();
            Item i = (Item) ite.next();
            item.put("manufactor", i.getManufactor());
            item.put("model", i.getModel());
            item.put("price", i.getPrice());
            item.put("quantity", i.getQuantity());
            table.put("Item2" + i, item);
        }
        return table;
    }
}
