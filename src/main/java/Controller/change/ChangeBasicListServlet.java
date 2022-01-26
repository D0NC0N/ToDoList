package Controller.change;

import Model.data.BasicList;
import Model.data.ToDoListData;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "ChangeBasicListServlet", value = "/ChangeBasicListServlet")
public class ChangeBasicListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            int basicId = Integer.parseInt(request.getParameter("basicId"));

            request.setAttribute("basicList", new ToDoListData().getInstance().getBasicListById(basicId));

            request.getRequestDispatcher("/View/changeBasicList.jsp").include(request, response);
        }
        else {
            request.getServletContext().getRequestDispatcher("/View/logIn.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            int basicId = Integer.parseInt(request.getParameter("basicId"));
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

            ToDoListData toDoListData = new ToDoListData().getInstance();

            ArrayList<String> subTasks = toDoListData.getBasicListById(basicId).getSubTasks();
            BasicList newBasicList = new BasicList(basicId, basicName, priority, time, comment, subTasks);

            toDoListData.changeBasicList(newBasicList, userId, current);


            request.setAttribute("lists", toDoListData.getToDoLists(userId, current));
            request.getServletContext().getRequestDispatcher("/View/index.jsp").forward(request, response);
        }
        else {
            request.getServletContext().getRequestDispatcher("/View/logIn.jsp").forward(request, response);
        }
    }
}
