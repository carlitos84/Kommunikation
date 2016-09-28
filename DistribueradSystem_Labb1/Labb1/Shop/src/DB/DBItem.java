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
        return getItemsByQuery(query);
    }

    public static ArrayList searchItemByModel(String model)
    {
        String query = "SELECT * FROM t_item WHERE K_Model = " + "\"" + model + "\";";
        return getItemsByQuery(query);
    }

    private static ArrayList getItemsByQuery(String query)
    {
        ArrayList<DBItem> itemList = new ArrayList<>();
        try{
            DBManager cone = new DBManager();
            Connection con = DBManager.getCon();
            System.out.println("before creating statement");
            Statement st = con.createStatement();
            System.out.println("after creating statement");
            ResultSet rs = st.executeQuery(query);
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
