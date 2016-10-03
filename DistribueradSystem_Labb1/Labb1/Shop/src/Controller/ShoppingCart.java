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
    private int totalPrice;

    public ShoppingCart()
    {
        shoppingcart = new ArrayList<ItemDTO>();
        totalPrice = 0;
    }

    public void addToCart(Hashtable item, int amountToAdd)
    {
        ItemDTO itemToAdd = new ItemDTO((int)item.get("id"), (String)item.get("manufactor"), (String)item.get("model"), (int)item.get("price"), amountToAdd);
        totalPrice += itemToAdd.getPrice();
        int index = itemIdExistAt(itemToAdd.getId());
        if(index >=0)
        {
            shoppingcart.get(index).addQtyInShoppingCart(amountToAdd);
        }
        else
        {
            shoppingcart.add(itemToAdd);
        }

    }

    private int itemIdExistAt(int id)
    {
        int existAt = -1;
        for (int i=0;i<shoppingcart.size();i++)
        {
            if(shoppingcart.get(i).getId() == id)
            {
                existAt = i;
            }
        }
        return existAt;
    }

    public int getSize()
    {
        return shoppingcart.size();
    }

    public void removeItem(int index, int amount)
    {
        ItemDTO item = shoppingcart.get(index);

        if(item.getQtyInShoppingCart() - amount <= 0)
        {
            shoppingcart.remove(item);
            totalPrice -= item.getPrice()*item.getQtyInShoppingCart();
        }
        else
        {
            item.removeQtyInShoppingCart(amount);
            totalPrice -= item.getPrice()*amount;
        }
    }

    public void emptycart()
    {
        shoppingcart = new ArrayList<ItemDTO>();
        totalPrice = 0;
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
        return table;
    }

    public ItemDTO getItem(int index)
    {
        return shoppingcart.get(index);
    }

    public int getTotalPrice()
    {
        return this.totalPrice;
    }

    public class ItemDTO
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
        public void addQtyInShoppingCart(int amount) { this.qtyInShoppingCart += amount;}
        public void removeQtyInShoppingCart(int amount){ this.qtyInShoppingCart -= amount;}
    }
}
