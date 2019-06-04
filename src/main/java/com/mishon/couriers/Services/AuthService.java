package com.mishon.couriers.Services;

import com.mishon.couriers.DAO.*;
import com.mishon.couriers.*;
import com.mishon.couriers.Entities.Car;
import com.mishon.couriers.Entities.Request;
import com.mishon.couriers.Entities.Route;
import com.mishon.couriers.Entities.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class AuthService {
    public static void autentificate(HttpServletRequest request) throws SQLException {
        RequestDAO redao = (RequestDAO) BeanFactory.getBean(RequestDAO.class);
        List<Request> requests = redao.getRequestFromDB(-1,"","",-1,-1,0,-1);

        UserDAO udao = (UserDAO) BeanFactory.getBean(UserDAO.class);
        List<User> users = udao.getUserFromDB(-1,"","","");

        RouteDAO rdao = (RouteDAO) BeanFactory.getBean(RouteDAO.class);
        List<Route> routes = rdao.getRouteFromDB(-1,"","");

        CarDAO cdao = (CarDAO) BeanFactory.getBean(CarDAO.class);

        request.setAttribute("sessionId", 0);

        String pass = Service.getXSSSecureParameter(request.getParameter("pass"));
        if(pass.equals("")){
            pass = ",";
        }
        users = udao.getUserFromDB(-1, Service.getXSSSecureParameter(request.getParameter("login")),"", pass);


        if(users.size() >0) {
            if (users.get(0) != null) {
                List<Car> cars = cdao.getCarFromDB(-1, "", "", users.get(0).getId(), -1, -1);
                if (users.get(0).getType().equals("Driver")) {
                    DriverService.driverGetRequests(request, users.get(0).getId());
                } else {
                    request.setAttribute("size", requests.size());
                    request.setAttribute("sessionId", 100);
                    request.setAttribute("type", users.get(0).getType());
                    System.out.println(users.get(0).getType());
                    request.setAttribute("routeArr", routes);
                    request.setAttribute("reqArr", requests);

                    return;
                }
            }
        }

    }
}
