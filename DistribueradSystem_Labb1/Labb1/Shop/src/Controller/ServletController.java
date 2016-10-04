package Controller;

import BO.BOManager;
import BO.Item;
import BO.LookItems;
import DB.DBItem;
import DB.DBManager;

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
    User user ;
    boolean validation;
    int resultsize;
    int ordersize;
    int allorderssize;
    int staffworkingorderid;
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        BOManager.init();
        bo = new BOManager();
        System.out.println("init");
        cart = new ShoppingCart();
        user = new User();
        validation = false;
        resultsize = 0;
        ordersize = 0;
        allorderssize = 0;
        staffworkingorderid = 0;
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
        String tmpusername = request.getParameter("usrname");
        String tmppassword = request.getParameter("psw");
        String usertype = request.getParameter("usertype");
        String logoutbutton = request.getParameter("logoutbutton");
        String makeorderbutton = request.getParameter("makeorderbutton");

        System.out.println("************start doPost*********");

        System.out.println("MakeorderButton : "+ makeorderbutton);
        if(makeorderbutton != null)
        {
            int result = BOManager.makeOrder(cart, user.getUsername(), user.getPassword());
            if(result == 200)
            {
                cart.emptycart();
            }
            System.out.println("Result of ORDER: " + result);
        }

        if(logoutbutton != null)
        {
            validation = false;
            request.getSession().setAttribute("validation", validation);
            user = new User();
            cart.emptycart();
            request.getSession().setAttribute("totalprice",cart.getTotalPrice());
            request.getSession().setAttribute("shoppingcarttable", cart.lookCart());
            request.getSession().setAttribute("shoppingcartsize", cart.getSize());
            request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
        }

        //validation of user
        if(validation == false && user.validateUser(tmpusername, tmppassword, usertype))
        {
            request.getSession().setAttribute("username",user.getUsername());
            validation = true;
            request.getSession().setAttribute("validation", validation);
            switch (usertype)
            {
                case "customer":
                    request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
                    break;
                case "staff":
                    request.getRequestDispatcher("/WEB-INF/index_staff.jsp").forward(request, response);
                    break;
                case "admin":
                    request.getRequestDispatcher("/WEB-INF/index_admin.jsp").forward(request, response);
                    break;
                default:
                    System.out.println("validation of user default!!");
            }
        }

        //to remove a certain item
        for(int i=0;i<cart.getSize();i++)
        {
            String buttonRemove = request.getParameter("removebutton"+i);
            System.out.println("buttonResult:" + buttonRemove);
            if( buttonRemove != null)
            {
                System.out.println("buttonResult in not null:" + buttonRemove + "   amountfield: " + request.getSession().getAttribute("amountremovefield"+i));
                int amounttoremove = Integer.parseInt(request.getParameter("amountremovefield"+i));
                cart.removeItem(i, amounttoremove);
            }
        }

        // Get the size of the result hashtable to add item to cart.
        resultsize = (Integer)request.getSession().getAttribute("resultsize");
        System.out.println("sizeOfResult = " + resultsize);
        if(searchString == null)
        {
            for(int i=0;i<resultsize;i++)
            {
                String buttonResult = request.getParameter("button"+i);
                System.out.println("buttonResult:" + buttonResult);
                if( buttonResult != null)
                {
                    System.out.println("buttonResult in not null:" + buttonResult + "   amountfield: " + request.getParameter("amountfield"+i));

                    Hashtable resultTable = (Hashtable) request.getSession().getAttribute("resulttable");
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

        }

        //display search result
        if(searchString != null) {
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
            session.setAttribute("resultsize", table.get("size"));
            session.setAttribute("resulttable", table);
        }

        //staff work:
        System.out.println("user is staff =" + user.isStaff());
        if(user.isStaff())
        {
            String showallorders = request.getParameter("showallorders");
            String packetorder = request.getParameter("packetorder");

            if(showallorders != null)
            {
                System.out.println(" in showallordersButton");
                Hashtable allOrders = BOManager.getAllOrders();
                System.out.println("allorders table size: " + allOrders.size());
                request.getSession().setAttribute("orderlist", allOrders);
                allorderssize = allOrders.size();
                request.getSession().setAttribute("allorderlistsize", allorderssize);
            }

            System.out.println("allorderssize: " + allorderssize);
            for (int i=0; i<allorderssize;i++)
            {
                String showbutton = request.getParameter("showorderbutton"+i);
                if(showbutton != null)
                {
                    String idfillednumber = request.getParameter("idfillednumber"+i);
                    staffworkingorderid = Integer.parseInt(idfillednumber);
                    Hashtable order = BOManager.getItemsInOrderByOrderId(staffworkingorderid);
                    request.getSession().setAttribute("ordersize", order.size());
                    request.getSession().setAttribute("showorder",order);
                    request.getSession().setAttribute("ordertotalprice",order.get("totalprice"));
                }
            }

            if(packetorder!=null)
            {

                int result = BOManager.packetOrderByStaffWithOrderId(staffworkingorderid, user.getUsername(), user.getPassword());
                System.out.println(result);
                if(result==200)
                {
                    ordersize = 0;
                    request.getSession().setAttribute("ordersize", ordersize);
                    Hashtable allOrders = BOManager.getAllOrders();
                    request.getSession().setAttribute("orderlist", allOrders);
                    allorderssize = allOrders.size();
                    request.getSession().setAttribute("allorderlistsize", allorderssize);
                }
            }

            request.getRequestDispatcher("/WEB-INF/index_staff.jsp").forward(request, response);
        }

        request.getSession().setAttribute("allorderlistsize", allorderssize);
        request.getSession().setAttribute("totalprice",cart.getTotalPrice());
        if(user.isCustomer())
        {
            request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
        }

        System.out.println("************end doPost*********");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("*************************in doGet");
        request.getSession().setAttribute("validation", validation);
        request.getSession().setAttribute("resultsize", resultsize);
        request.getSession().setAttribute("shoppingcartsize", cart.getSize());
        request.getSession().setAttribute("shoppingcarttable", cart.lookCart());
        request.getSession().setAttribute("totalprice", cart.getTotalPrice());
        request.getSession().setAttribute("allorderlistsize", allorderssize);
        request.getSession().setAttribute("allorderlistsize", allorderssize);
        System.out.println("total price: " + cart.getTotalPrice());
        request.getSession().setAttribute("ordersize", ordersize);
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }
}
