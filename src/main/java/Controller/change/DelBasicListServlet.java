package Controller.change;

import Model.data.ToDoListData;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "DelBasicListServlet", value = "/DelBasicListServlet")
public class DelBasicListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            int basicId = Integer.parseInt(request.getParameter("basicId"));
            int userId = (int) session.getAttribute("userId");

            int year = (int) session.getAttribute("year");
            int month = (int) session.getAttribute("month");
            int date = (int) session.getAttribute("date");
            Date current = new Date(year, month, date);

            ToDoListData toDoListData = new ToDoListData().getInstance();
            toDoListData.deleteBasicList(basicId, userId, current);

            request.setAttribute("lists", toDoListData.getToDoLists(userId, current));
            request.getServletContext().getRequestDispatcher("/View/index.jsp").forward(request, response);
        }
        else {
            request.getServletContext().getRequestDispatcher("/View/logIn.jsp").forward(request, response);
        }
    }
}
