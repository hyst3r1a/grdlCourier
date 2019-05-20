package com.mishon.couriers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/main")
public class MainServlet extends HttpServlet {
    final static Logger logger = LogManager.getLogger("com.zetcode");
       protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           logger.info("post");
           request.setAttribute("sessionId", 0);


           switch(Service.getXSSSecureParameter(request.getParameter("sessionId"))){
               case "0":
                   Service.autentificate(request);
                   break;
               case "101":
                   Service.insertRequest(request);
                   break;

               case "103":
                   Service.assignDriver(request);
                   break;
               case "104":
                   Service.driverCompleteRequest(request);
                   break;

               case "106":
                   Service.driverFixCar(request);
                   break;
           }

           request.getRequestDispatcher("response.jsp").forward(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("get");
        request.setAttribute("sessionId", 0);


        switch(Service.getXSSSecureParameter(request.getParameter("sessionId"))){

            case "102":
                Service.getSuitableDrivers(request);
                break;

        }

        request.getRequestDispatcher("response.jsp").forward(request, response);
    }

}
