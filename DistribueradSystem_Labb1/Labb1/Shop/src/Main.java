import DB.DBCustomer;
import DB.DBItem;
import DB.DBManager;

import java.sql.SQLException;

/**
 * Created by Teddy on 2016-09-28.
 */
public class Main {
    public static void main(String args[])
    {
        DBManager conection = new DBManager();


        System.out.println( (DBItem.searchItemByModel("Les Paul")).toString() );
        //System.out.println( (DBCustomer.getCustomerByLogin("Carlos","jgfh")).toString() );
        try {
            DBManager.getCon().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
