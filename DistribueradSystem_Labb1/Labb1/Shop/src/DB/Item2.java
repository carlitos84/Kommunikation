package DB;

/**
 * Created by Teddy on 2016-09-28.
 */
public class Item2 {
    private int id;
    private String manufactor;
    private int price;
    private String model;
    private int quantity;

    public Item2(int id, String manufactor, int price, String model, int quantity)
    {
        this.id = id;
        this.manufactor = manufactor;
        this.price = price;
        this.model = model;
        this.quantity = quantity;
    }

    public String toString()
    {
        return  "\n" + String.valueOf(id) + " " + manufactor + " " + String.valueOf(price) + " " + model + " " + String.valueOf(quantity);
    }


}
