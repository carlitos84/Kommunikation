package BO;

import DB.DBItem;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by Teddy on 2016-09-28.
 */
/***
 * LookItems converts a list, ArrayList containing items, to a readable table for viewpart(JSP:n) and therefore be viewable to a user.
 *
 * */
public class LookItems {
    //konverterar till en läsbartabell för JSP

    public Hashtable getItemsWithManufactor(String manufactor){
        ArrayList<Item> itemList = Item.searchItemsByManufactor(manufactor);
        return getItemHashtable(itemList);
    }

    public Hashtable getItemsWithModel(String model)
    {
        ArrayList<Item> itemList = Item.searchItemsByModel(model);
        return getItemHashtable(itemList);
    }

    public Hashtable getItemsWithId(int itemId)
    {
        Item i = Item.searchItemsById(itemId);
        ArrayList<Item> itemList = new ArrayList<Item>();
        itemList.add(i);
        return getItemHashtable(itemList);
    }

    private Hashtable getItemHashtable(ArrayList itemList)
    {
        Hashtable table = new Hashtable();
        table.put("size", itemList.size());

        Iterator it = itemList.iterator();
        for(int i=0; it.hasNext(); i++)
        {
            Hashtable item = new Hashtable();
            Item itemTmp = (Item) it.next();
            System.out.print(itemTmp.getManufactor());
            item.put("id", itemTmp.getId());
            item.put("manufactor", itemTmp.getManufactor());
            item.put("model", itemTmp.getModel());
            item.put("price", itemTmp.getPrice());
            item.put("quantity", itemTmp.getQuantity());

            table.put("Item" + i, item);
        }
        return table;
    }
}
