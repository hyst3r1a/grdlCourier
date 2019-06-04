package com.mishon.couriers.Servlets;

import com.mishon.couriers.Services.RequestService;
import com.mishon.couriers.Services.Service;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/reqinsert")
public class ReqInsertServlet extends HttpServlet {
    final static Logger logger = LogManager.getLogger("com.zetcode");
       protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           logger.info("post");
           request.setAttribute("sessionId", 0);


           if (Service.getXSSSecureParameter(request.getParameter("sessionId")).equals("101")) {


               try {
                   RequestService.insertRequest(request);
               } catch (SQLException e) {
                   request.getRequestDispatcher("error.jsp").forward(request, response);
                   logger.log(Level.ERROR, "SQLError");
                   return;
               }


               request.getRequestDispatcher("response.jsp").forward(request, response);
           }

       }
}
