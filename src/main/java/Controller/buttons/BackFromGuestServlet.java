package Controller.buttons;

import Model.data.ToDoList;
import Model.data.ToDoListData;
import Model.data.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "BackFromGuestServlet", value = "/BackFromGuestServlet")
public class BackFromGuestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            int year = (int) session.getAttribute("year");
            int month = (int) session.getAttribute("month");
            int date = (int) session.getAttribute("date");

            Date current = new Date(year, month, date);
            int userId = (int) session.getAttribute("userId");

            String userName = (String) session.getAttribute("login");

            ToDoListData toDoListData = new ToDoListData().getInstance();
            ArrayList<ToDoList> toDoLists = toDoListData.getToDoLists(userId, current);

            if (toDoLists.size() == 0) {
                ToDoList newToDoList = new ToDoList(userId, userName, current);
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {

        }
        else {
            request.getServletContext().getRequestDispatcher("/View/logIn.jsp").forward(request, response);
        }
    }
}
