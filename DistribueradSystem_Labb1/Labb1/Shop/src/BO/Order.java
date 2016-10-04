package BO;

import DB.DBOrder;

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

    //staff:
    public void addItem(int itemId,int amount, int customerId, int orderId)
    {
        orderList.add(new ItemDTO(itemId, amount, customerId, orderId));
        size = orderList.size();
    }

    public static ArrayList<int[]> getAllOrders()
    {
        return DBOrder.getAllOrders();
    }
    public static Order getAllItemsInOrder(int orderId)
    {
        return DBOrder.getItemInOrderByOrderId(orderId);
    }

    public int getCustomerIdFrom(int index)
    {
        return orderList.get(index).customerId;
    }
    public int getOrderIdFrom(int index)
    {
        return orderList.get(index).orderId;
    }

    private class ItemDTO{
        private int itemId;
        private int amount;
        private int customerId;
        private int orderId;
        public ItemDTO(int itemId,int amount)
        {
            this.itemId = itemId;
            this.amount =amount ;
        }

        public ItemDTO(int itemId,int amount, int CustomerId, int OrderId)
        {
            this.itemId = itemId;
            this.amount =amount ;
            this.customerId = customerId;
            this.orderId = orderId;
        }
    }

}
