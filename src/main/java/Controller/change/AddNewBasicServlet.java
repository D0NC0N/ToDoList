package Controller.change;

import Model.data.BasicList;
import Model.data.ToDoListData;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "AddNewBasicServlet", value = "/AddNewBasicServlet")
public class AddNewBasicServlet extends HttpServlet {
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

            int year = (int) session.getAttribute("year");
            int month = (int) session.getAttribute("month");
            int date = (int) session.getAttribute("date");

            String comment = request.getParameter("comment");

            //добавить newBasicList в toDoList
            Date current = new Date(year, month, date);
            int userId = (int) session.getAttribute("userId");

            BasicList newBasicList = new BasicList(basicName, priority, time, comment);
            ToDoListData toDoListData = new ToDoListData().getInstance();
            toDoListData.addNewBasicList(newBasicList, userId, current);


            request.setAttribute("lists", toDoListData.getToDoLists(userId, current));
            request.getServletContext().getRequestDispatcher("/View/index.jsp").forward(request, response);
        }
        else {
            request.getServletContext().getRequestDispatcher("/View/logIn.jsp").forward(request, response);
        }
    }
}
