package Controller;

import BO.BOManager;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Teddy on 2016-09-29.
 */
@WebServlet(name = "ServletController", urlPatterns = "/index.jsp")
public class ServletController extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException
    {
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
        doGet(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String search = (String) request.getParameter("googlesearch");
        System.out.println(search);
        String t = "detta är ett test";
        request.getSession().setAttribute("test", t);
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }
}
