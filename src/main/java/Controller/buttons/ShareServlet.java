package Controller.buttons;

import Model.IO.Pair;
import Model.IO.Trinity;
import Model.data.ToDoList;
import Model.data.ToDoListData;
import Model.data.UserData;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "ShareServlet", value = "/ShareServlet")
public class ShareServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {

            int year = (int) session.getAttribute("year");
            int month = (int) session.getAttribute("month");
            int date = (int) session.getAttribute("date");
            Date current = new Date(year, month, date);
            int userId = (int) session.getAttribute("userId");

            ToDoListData toDoListData = new ToDoListData().getInstance();
            request.setAttribute("lists", toDoListData.getToDoLists(userId, current));

            ArrayList<Trinity<Integer, String, String>> usersIdAndNames = new ArrayList<>();

            UserData userData = new UserData().getInstance();
            Trinity trinity;
            ToDoList toDoList = toDoListData.getToDoLists(userId, current).get(0);

            for (int i = 0; i < userData.getUsersInfo().size(); ++i) {
                if (userData.getUsersInfo().get(i).getGroup() != userId) {
                    for (int j = 0; j < toDoList.getWhoCanWatch().size(); ++j) {
                        if (toDoList.getWhoCanWatch().get(j).getGroup() == userData.getUsersInfo().get(i).getGroup()) {

                            String tmp = toDoList.getWhoCanWatch().get(j).getSubgroup() ? "C" : "R";

                            trinity = new Trinity(userData.getUsersInfo().get(i).getGroup(),
                                    userData.getUsersInfo().get(i).getSubgroup().getFirst(),
                                    tmp);
                            usersIdAndNames.add(trinity);
                            break;
                        } else if (j + 1 == toDoList.getWhoCanWatch().size()) {
                            trinity = new Trinity(userData.getUsersInfo().get(i).getGroup(),
                                    userData.getUsersInfo().get(i).getSubgroup().getFirst(),
                                    "X");
                            usersIdAndNames.add(trinity);
                        }
                    }

                }
            }

            request.setAttribute("usersInfo", usersIdAndNames);

            request.getRequestDispatcher("/View/share.jsp").include(request, response);
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

            ArrayList<Pair<Integer, Boolean>> newWhoCanWatch = new ArrayList<>();

            UserData userData = new UserData().getInstance();
            int userSize = userData.getUsersInfo().size();
            int valId;
            boolean bool;
            newWhoCanWatch.add(new Pair<>(userId, true));
            for (int i = 0; i < userSize; ++i) {
                if (i != userId) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(i);
                    valId = Integer.parseInt(request.getParameter(sb.toString()));
                    if (valId != -1) {
                        bool = (valId == 1);
                        newWhoCanWatch.add(new Pair<>(i, bool));
                    }
                }
            }

            ToDoListData toDoListData = new ToDoListData().getInstance();
            toDoListData.changeWhoCanWatch(newWhoCanWatch, userId, current);


            request.setAttribute("lists", toDoListData.getToDoLists(userId, current));
            request.getServletContext().getRequestDispatcher("/View/index.jsp").forward(request, response);
        }
        else {
            request.getServletContext().getRequestDispatcher("/View/logIn.jsp").forward(request, response);
        }
    }
}
