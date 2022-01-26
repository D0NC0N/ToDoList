package Controller.guest;

import Model.data.BasicList;
import Model.data.ToDoListData;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "GuestAddBasicServlet", value = "/GuestAddBasicServlet")
public class GuestAddBasicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            request.getRequestDispatcher("/View/addNewBasicList.html").include(request, response);
        }
        else {
            request.getServletContext().getRequestDispatcher("/View/logIn.jsp").forward(request, response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            String basicName = request.getParameter("basicName");
            int priority = Integer.parseInt(request.getParameter("priority"));
            String time = request.getParameter("deadLine");//12:00

            int year = (int) session.getAttribute("guestYear");
            int month = (int) session.getAttribute("guestMonth");
            int date = (int) session.getAttribute("guestDate");

            Date current = new Date(year, month, date);
            int userId = (int) session.getAttribute("guestUserId");


            String comment = request.getParameter("comment");
            BasicList newBasicList = new BasicList(basicName, priority, time, comment);
            ToDoListData toDoListData = new ToDoListData().getInstance();
            toDoListData.addNewBasicList(newBasicList, userId, current);


            request.setAttribute("lists", toDoListData.getToDoLists(userId, current));
            request.getServletContext().getRequestDispatcher("/View/watchC.jsp").forward(request, response);
        }
        else {
            request.getServletContext().getRequestDispatcher("/View/logIn.jsp").forward(request, response);
        }
    }
}
