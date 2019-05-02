
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
       protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            DataConnection conn = new DataConnection();
            conn.testGet();


        String name = request.getParameter("login");
           if (name.equals("Driver")) {
               request.setAttribute("user", name);
               request.setAttribute("sessionid", 100);
               request.setAttribute("type", "driver");
           }
        if (name.equals("Manager") && !name.equals("Driver")) {
            request.setAttribute("user", name);
            request.setAttribute("sessionid", 100);
            request.setAttribute("type", "manager");
        }else {request.setAttribute("user",  conn.testGet());
            request.setAttribute("sessionid", 100);
            request.setAttribute("type", "manager");}
        request.getRequestDispatcher("response.jsp").forward(request, response);
    }
}
