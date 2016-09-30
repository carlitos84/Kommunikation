package BO;

import DB.DBItem;

import java.util.ArrayList;

/**
 * Created by Teddy on 2016-09-28.
 */
public class Item {
    private int id;
    private String manufactor;
    private int price;
    private String model;
    private int quantity;

    public static ArrayList searchItemsByManufactor(String manufactor)
    {
        return DBItem.searchItemByManufactor(manufactor);
    }

    public static ArrayList searchItemsByModel(String model)
    {
        return DBItem.searchItemByModel(model);
    }

    public static Item searchItemsById(int itemId)
    {
        return DBItem.searchItemById(itemId);
    }

    public Item(int id, String manufactor, int price, String model, int quantity)
    {
        this.id = id;
        this.manufactor = manufactor;
        this.price = price;
        this.model = model;
        this.quantity = quantity;
    }

    public String getManufactor()
    {
        return manufactor;
    }

    public int getPrice()
    {
        return price;
    }

    public String getModel()
    {
        return model;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public int getId(){return id;}

    public String toString()
    {
        return  "\n" + String.valueOf(id) + " " + manufactor + " " + String.valueOf(price) + " " + model + " " + String.valueOf(quantity);
    }

}
