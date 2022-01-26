package Controller.buttons;

import Model.data.ToDoListData;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "SortServlet", value = "/SortServlet")
public class SortServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            doPost(request, response);
        } else {
            request.getServletContext().getRequestDispatcher("/View/logIn.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            int year = (int) session.getAttribute("year");
            int month = (int) session.getAttribute("month");
            int date = (int) session.getAttribute("date");

            Date current = new Date(year, month, date);
            int userId = (int) session.getAttribute("userId");

            String sortBy = request.getParameter("sort");

            ToDoListData toDoListData = new ToDoListData().getInstance();

            int id = toDoListData.getToDoLists(userId, current).get(0).getToDoListId();
            switch (sortBy) {
                case "Name":
                    toDoListData.sortBasicByName(id);
                    break;
                case "Time":
                    toDoListData.sortBasicBySubDeadline(id);
                    break;
                case "Priority":
                    toDoListData.sortBasicByPriority(id);
                    break;
            }

            request.setAttribute("lists", toDoListData.getToDoLists(userId, current));
            request.getServletContext().getRequestDispatcher("/View/index.jsp").forward(request, response);
        } else {
            request.getServletContext().getRequestDispatcher("/View/logIn.jsp").forward(request, response);
        }
    }
}
