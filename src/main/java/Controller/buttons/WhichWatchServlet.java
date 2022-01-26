package Controller.buttons;

import Model.data.ToDoListData;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "WhichWatchServlet", value = "/WhichWatchServlet")
public class WhichWatchServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            String str = request.getParameter("subIt");

            String[] mass = str.split(":");

            int toDoListId = Integer.parseInt(mass[0]);

            String which = mass[1];

            ToDoListData toDoListData = new ToDoListData().getInstance();

            session.setAttribute("guestYear", toDoListData.guestToDoList(toDoListId).get(0).getDay().getYear());
            session.setAttribute("guestMonth", toDoListData.guestToDoList(toDoListId).get(0).getDay().getMonth());
            session.setAttribute("guestDate", toDoListData.guestToDoList(toDoListId).get(0).getDay().getDate());
            session.setAttribute("guestUserId", toDoListData.guestToDoList(toDoListId).get(0).getUserId());

            request.setAttribute("lists", toDoListData.guestToDoList(toDoListId));

            if (which.equals("Read")) {
                request.getRequestDispatcher("/View/watchR.jsp").include(request, response);
            }
            else if (which.equals("Change")) {
                request.getRequestDispatcher("/View/watchC.jsp").include(request, response);
            }
        }
        else {
            request.getServletContext().getRequestDispatcher("/View/logIn.jsp").forward(request, response);
        }
    }
}
