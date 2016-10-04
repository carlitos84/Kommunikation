package BO;

import DB.DBItem;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by Teddy on 2016-10-04.
 */
/**
 *  LookOrders will convert a list of orders or an order, ArrayList with items, to a viewable table for the user.
 * */
public class LookOrders {
    public Hashtable getAllOrders()
    {
        ArrayList<int[]> orderList = Order.getAllOrders();
        return getAllOrderHashtable(orderList);
    }

    public Hashtable getOrderByOrderId(int orderId)
    {
        Order order = Order.getAllItemsInOrder(orderId);
        return getOrderHashtable(order);
    }

    private Hashtable getOrderHashtable(Order order)
    {
        Hashtable table = new Hashtable();
        table.put("size", order.getSize());
        int totalprice = 0;

        for(int i =0; i<order.getSize();i++)
        {
            Hashtable item = new Hashtable();
            Item tmpItem = DBItem.searchItemById(order.getItemIdFrom(i));

            item.put("itemId", tmpItem.getId());

            item.put("quantity",order.getItemAmountToBuyFrom(i));

            int price = tmpItem.getPrice();
            totalprice += price*order.getItemAmountToBuyFrom(i);
            table.put("OrderItem" + i, item);
        }
        table.put("ordertotalprice", totalprice);

        return table;
    }

    private Hashtable getAllOrderHashtable(ArrayList itemList)
    {
        Hashtable table = new Hashtable();
        table.put("size", itemList.size());
        Iterator it = itemList.iterator();

        for(int i=0; it.hasNext(); i++)
        {
            Hashtable item = new Hashtable();
            int[] order = (int[]) it.next();
            item.put("orderId", order[0]);
            System.out.println("order id "+ i + ": " + order[0]);
            item.put("customerId",order[1]);
            System.out.println("customer id "+ i + ": " + order[1]);
            table.put("Order" + i, item);
        }
        return table;
    }
}
