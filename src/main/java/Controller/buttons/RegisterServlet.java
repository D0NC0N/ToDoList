package Controller.buttons;

import Model.data.ToDoList;
import Model.data.ToDoListData;
import Model.data.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
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
            HttpSession session = request.getSession();
            Date current = new Date();
            current.setHours(0);
            current.setMinutes(0);
            current.setSeconds(0);
            session.setAttribute("year", current.getYear());
            session.setAttribute("month", current.getMonth());
            session.setAttribute("date", current.getDate());
            User newUser = new User(login, password, current); // создали нового user-а
            newUser.addUser(login, password);

            session.setAttribute("login", login); // занесли в session name и Id user-а
            session.setAttribute("userId", newUser.getUserId());


            ToDoListData toDoListData = new ToDoListData().getInstance();
            ArrayList<ToDoList> lists = newUser.getToDoLists();
            ToDoList newToDoList = new ToDoList(newUser.getUserId(), login, current);
            lists.add(newToDoList);
            toDoListData.addNewToDoList(newToDoList);

            request.setAttribute("lists", lists);
            request.getServletContext().getRequestDispatcher("/View/index.jsp").forward(request, response);
        }
        else {
            request.getServletContext().getRequestDispatcher("/View/logIn.jsp").forward(request, response);
        }

        out.println("</html></body>");
        out.close();
    }
}
