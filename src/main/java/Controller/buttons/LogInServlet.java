package Controller.buttons;

import Model.data.ToDoList;
import Model.data.ToDoListData;
import Model.data.User;
import Model.data.UserData;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "LogInServlet", value = "/LogInServlet")
public class LogInServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        if (login != null) {
            if (new UserData().getInstance().checkUserData(login, password)) {
                HttpSession session = request.getSession();
                Date current = new Date();
                current.setHours(0);
                current.setMinutes(0);
                current.setSeconds(0);
                session.setAttribute("year", current.getYear());
                session.setAttribute("month", current.getMonth());
                session.setAttribute("date", current.getDate());

                User user = new User(login, password, current);

                session.setAttribute("login", login);
                session.setAttribute("userId", user.getUserId());

                ArrayList<ToDoList> lists = user.getToDoLists();
                if (lists.size() == 0) {
                    ToDoListData toDoListData = new ToDoListData().getInstance();
                    ToDoList newToDoList = new ToDoList(user.getUserId(), login, current);
                    lists.add(newToDoList);
                    toDoListData.addNewToDoList(newToDoList);
                }
                request.setAttribute("lists", lists);

                request.getServletContext().getRequestDispatcher("/View/index.jsp").forward(request, response);
            }
            else {
                out.println("<h2>Please, try to log in again</h2>");
                request.getServletContext().getRequestDispatcher("/View/logIn.jsp").include(request, response);
            }
        }
        else {
            request.getServletContext().getRequestDispatcher("/View/logIn.jsp").forward(request, response);
        }

        out.println("</html></body>");
        out.close();
    }
}
