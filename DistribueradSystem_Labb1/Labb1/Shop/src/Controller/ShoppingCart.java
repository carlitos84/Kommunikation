package Controller;

import BO.Item;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by Teddy on 2016-09-29.
 */
public class ShoppingCart {
    private ArrayList<ItemDTO> shoppingcart;

    public ShoppingCart()
    {
        shoppingcart = new ArrayList<ItemDTO>();
    }

    public void addToCart(Hashtable item, int amountToAdd)
    {
        ItemDTO itemToAdd = new ItemDTO((int)item.get("id"), (String)item.get("manufactor"), (String)item.get("model"), (int)item.get("price"), amountToAdd);
        shoppingcart.add(itemToAdd);
    }

    public int getSize()
    {
        return shoppingcart.size();
    }

    public Hashtable lookCart()
    {
        Hashtable table = new Hashtable();
        table.put("size", shoppingcart.size());

        Iterator it = shoppingcart.iterator();
        for(int i=0; it.hasNext(); i++)
        {
            Hashtable item = new Hashtable();
            ItemDTO itemTmp = (ItemDTO) it.next();

            item.put("manufactor", itemTmp.getManufactor());
            item.put("model", itemTmp.getModel());
            item.put("price", itemTmp.getPrice());
            item.put("quantity", itemTmp.getQtyInShoppingCart());
            table.put("Item" + i, item);
        }

        /*Hashtable table = new Hashtable();
       // table.put("size", shoppingcart.size());
        Iterator ite = shoppingcart.iterator();
        while (ite.hasNext())
        {
            Hashtable item = new Hashtable();
            ItemDTO i = (ItemDTO) ite.next();
            item.put("manufactor", i.getManufactor());
            item.put("model", i.getModel());
            item.put("price", i.getPrice());
            item.put("quantity", i.getQtyInShoppingCart());
            table.put("Item" + i, item);
        }*/
        return table;
    }

    private class ItemDTO
    {
        private int id;
        private String manufactor;
        private String model;
        private int price;
        private int qtyInShoppingCart;

        private ItemDTO(int id, String manufactor, String model, int price, int qtyInShoppingCart)
        {
            this.id = id;
            this.manufactor = manufactor;
            this.model = model;
            this.price = price;
            this.qtyInShoppingCart = qtyInShoppingCart;
        }

        public int getId() {
            return id;
        }

        public String getManufactor() {
            return manufactor;
        }

        public String getModel() {
            return model;
        }

        public int getPrice() {
            return price;
        }

        public int getQtyInShoppingCart() {
            return qtyInShoppingCart;
        }
    }
}
