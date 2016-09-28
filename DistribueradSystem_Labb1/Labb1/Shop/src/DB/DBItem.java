package DB;
import BO.Item;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Teddy on 2016-09-28.
 */
public class DBItem extends Item {

    public static ArrayList searchItemByManufactor(String itemManufactor)
    {
        String query = "SELECT * FROM t_item WHERE K_Manufactor = " +"\"" + itemManufactor +"\";";
        return getItemsWithQuery(query);
    }

    public static ArrayList searchItemByModel(String model)
    {
        String query = "SELECT * FROM t_item WHERE K_Model = " + "\"" + model + "\";";
        return getItemsWithQuery(query);
    }

    public static DBItem searchItemById(int itemId)
    {
        String query = "SELECT * FROM t_item WHERE K_Id = " + "\"" + itemId + "\";";
        return (DBItem)getItemsWithQuery(query).get(0);
    }

    private static ArrayList getItemsWithQuery(String query)
    {
        ArrayList<DBItem> itemList = new ArrayList<>();
        try{
            ResultSet rs = DBManager.getItemsByQuery(query);
            while(rs.next())
            {
                int id = rs.getInt("K_Id");
                String name = rs.getString("K_Manufactor");
                int price = rs.getInt("K_Price");
                String model = rs.getString("K_Model");
                int quantity = rs.getInt("K_Quantity");
                itemList.add(new DBItem(id ,name,price,model, quantity));
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return itemList;
    }

    private DBItem(int id, String name, int price, String model, int quantity)
    {
        super(id, name, price, model, quantity);
    }
}
