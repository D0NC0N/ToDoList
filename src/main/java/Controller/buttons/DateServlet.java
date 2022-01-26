package Controller.buttons;

import Model.data.ToDoList;
import Model.data.ToDoListData;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "DateServlet", value = "/DateServlet")
public class DateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            String day = request.getParameter("date");//2021-12-23
            String[] words = day.split("-");

            int year = Integer.parseInt(words[0]) - 1900;
            int month = Integer.parseInt(words[1]) - 1;
            int date = Integer.parseInt(words[2]);

            Date requestDate = new Date(year, month, date);
            session.setAttribute("year", year);
            session.setAttribute("month", month);
            session.setAttribute("date", date);

            int userId = (int) session.getAttribute("userId");
            String userName = (String) session.getAttribute("login");

            ToDoListData toDoListData = new ToDoListData().getInstance();
            ArrayList<ToDoList> toDoLists = toDoListData.getToDoLists(userId, requestDate);

            if (toDoLists.size() == 0) {
                ToDoList newToDoList = new ToDoList(userId, userName, requestDate);
                toDoLists.add(newToDoList);
                toDoListData.addNewToDoList(newToDoList);
            }
            request.setAttribute("lists", toDoLists);

            request.getServletContext().getRequestDispatcher("/View/index.jsp").forward(request, response);
        }
        else {
            request.getServletContext().getRequestDispatcher("/View/logIn.jsp").forward(request, response);
        }
    }
}
