package BO;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by Teddy on 2016-09-28.
 */
public class Order {
    private int size;
    private String username;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    private ArrayList<ItemDTO> orderList;

    public Order()
    {
        orderList = new ArrayList<>();
        size = 0;
    }

    public int getSize()
    {
        return size;
    }

    public int getItemIdFrom(int index)
    {
       return orderList.get(index).itemId;
    }
    public int getItemAmountToBuyFrom(int index)
    {
       return orderList.get(index).amount;
    }

    public void addItem(int itemId,int amount)
    {
        orderList.add(new ItemDTO(itemId, amount));
        size = orderList.size();
    }

    private class ItemDTO{
        private int itemId;
        private int amount;

        public ItemDTO(int itemId,int amount)
        {
            this.itemId = itemId;
            this.amount =amount ;
        }
    }

}
