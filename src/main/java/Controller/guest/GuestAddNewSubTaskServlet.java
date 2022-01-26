package Controller.guest;

import Model.data.ToDoListData;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "GuestAddNewSubTaskServlet", value = "/GuestAddNewSubTaskServlet")
public class GuestAddNewSubTaskServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            int basicId = Integer.parseInt(request.getParameter("basicId"));
            String sub = request.getParameter("newSubtask");

            ToDoListData toDoListData = new ToDoListData().getInstance();
            toDoListData.addSubTask(basicId, sub);

            int year = (int) session.getAttribute("guestYear");
            int month = (int) session.getAttribute("guestMonth");
            int date = (int) session.getAttribute("guestDate");

            Date current = new Date(year, month, date);
            int userId = (int) session.getAttribute("guestUserId");

            request.setAttribute("lists", toDoListData.getToDoLists(userId, current));
            request.getServletContext().getRequestDispatcher("/View/watchC.jsp").forward(request, response);
        }
        else {
            request.getServletContext().getRequestDispatcher("/View/logIn.jsp").forward(request, response);
        }
    }
}
