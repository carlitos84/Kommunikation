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

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        bo = new BOManager();
        System.out.println("init");
        BOManager.init();
    }

    @Override
    public void destroy()
    {
        //DB.DBManager.getCon().close();
        System.out.println("destroy");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("searchfield");
        if(search != null || !search.trim().equals("")) {
            String searchString = request.getParameter("searchfield");
            HttpSession session = request.getSession();
            Hashtable table = bo.searchByManufactor(searchString);

            session.setAttribute("size", table.get("size"));
            session.setAttribute("table", table);
            for(int i = 0; i < table.size(); i++) {
                System.out.println(table.get("Item" +i));
            }
            session.setAttribute("table", table);
            request.getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("KJhnsgd");
        String search = (String) request.getParameter("googlesearch");
        System.out.println(search);
        String t = "detta är ett test";
        request.getSession().setAttribute("test", t);
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);

    }
}
