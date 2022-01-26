package Controller.buttons;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LogOutServlet", value = "/LogOutServlet")
public class LogOutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        HttpSession session = request.getSession();
        session.invalidate();

        PrintWriter out=response.getWriter();
        out.println("<html>\n" +
                "<head>\n" +
                "    <link rel=\"stylesheet\" href=\"${pageContext.request.contextPath}/Style/style.css\"" +
                " type=\"text/css\">\n" +
                "</head>\n" +
                "<body>");

        out.println("<br><h1>You have successfully logged out!</h1>");
        request.getRequestDispatcher("/View/logIn.jsp").include(request, response);

        out.println("</html></body>");

        out.close();
    }
}
