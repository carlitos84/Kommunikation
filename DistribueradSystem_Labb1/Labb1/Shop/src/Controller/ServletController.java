package Controller;

import BO.BOManager;
import BO.Item;
import BO.LookItems;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Teddy on 2016-09-29.
 */
@WebServlet(name = "ServletController", urlPatterns = "/index.jsp")
public class ServletController extends HttpServlet {
    BOManager bo;
    ShoppingCart cart;
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        BOManager.init();
        bo = new BOManager();
        System.out.println("init");
        cart = new ShoppingCart();
    }

    @Override
    public void destroy()
    {
        //DB.DBManager.getCon().close();
        System.out.println("destroy");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchString = request.getParameter("searchfield");
        String searchBy = request.getParameter("searchBy");



        // Get the size of the result hashtable to add item to cart.
        Integer sizeOfResult = (Integer)request.getSession().getAttribute("size");
        System.out.println("sizeOfResult = " + sizeOfResult);
        if(sizeOfResult != null)
        {

            for(int i=0;i<sizeOfResult;i++)
            {
                String buttonResult = request.getParameter("button"+i);
                System.out.println("buttonResult:" + buttonResult);
                if( buttonResult != null)
                {
                    System.out.println("buttonResult in not null:" + buttonResult + "   amountfield: " + request.getParameter("amountfield"+i));

                    Hashtable resultTable = (Hashtable) request.getSession().getAttribute("table");
                    Hashtable itemAddToCart = (Hashtable) resultTable.get("Item"+i);
                    int amount = Integer.parseInt(request.getParameter("amountfield"+i));
                    cart.addToCart(itemAddToCart, amount);
                }
            }
            //cart table
            //System.out.println(cart.lookCart());
            request.getSession().setAttribute("shoppingcarttable", cart.lookCart());
            request.getSession().setAttribute("shoppingcartsize", cart.getSize());
            //cart table end
            request.getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
        }

        //display search result
        if(searchString != null || !searchString.trim().equals("")) {
            HttpSession session = request.getSession();

            Hashtable table = null;
            switch (searchBy)
            {
                case "manufactor":
                    table = bo.searchByManufactor(searchString);
                    break;
                case "model":
                    table = bo.searchByModel(searchString);
                    break;
                default:
                    System.out.println("In switch case default: " + searchBy);
            }
            session.setAttribute("shoppincarttable", cart.lookCart());
            session.setAttribute("shoppingcartsize", cart.getSize());
            session.setAttribute("size", table.get("size"));
            session.setAttribute("table", table);
            /*for(int i = 0; i < table.size(); i++) {
                System.out.println(table.get("Item" +i));
            }*/
            session.setAttribute("table", table);
            request.getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);

    }
}
