package com.mishon.couriers.Services;

import com.mishon.couriers.DAO.*;
import com.mishon.couriers.BeanFactory;
import com.mishon.couriers.Entities.Car;
import com.mishon.couriers.Entities.Request;
import com.mishon.couriers.Entities.Route;


import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class RequestService {
    public static void insertRequest(HttpServletRequest request) throws SQLException {
        RequestDAO redao = (RequestDAO) BeanFactory.getBean(RequestDAO.class);


        RouteDAO rdao =(RouteDAO) BeanFactory.getBean(RouteDAO.class);
        List<Route> routes = rdao.getRouteFromDB(-1,"","");

        redao.addRequestToDB(Service.getXSSSecureParameter(request.getParameter("issuer")),
                Service.getXSSSecureParameter(request.getParameter("date")),
                Service.getXSSSecureParameter(request.getParameter("req_capacity")),
                Service.getXSSSecureParameter(request.getParameter("price")), "0",
                Service.getXSSSecureParameter(request.getParameter("route")));


        List<Request> requests = redao.getRequestFromDB(-1,"","",-1,-1,0,-1);

        request.setAttribute("sessionId", 100);
        request.setAttribute("type","Manager");
        request.setAttribute("routeArr", routes);
        request.setAttribute("reqArr", requests);
    }
    public static void getSuitableDrivers(HttpServletRequest request) throws SQLException{
        RouteDAO rdao =(RouteDAO) BeanFactory.getBean(RouteDAO.class);
        List<Route> routes = rdao.getRouteFromDB(-1,"","");
        RequestDAO redao =(RequestDAO) BeanFactory.getBean(RequestDAO.class);
        List<Request> requests = redao.getRequestFromDB(Integer.parseInt(Service.getXSSSecureParameter(request.getParameter("reqID"))),"","",-1,-1,0,-1);
        CarDAO cdao = (CarDAO) BeanFactory.getBean(CarDAO.class);
        List<Car> cars = cdao.getCarFromDB(-1, "", "",-1, requests.get(0).getReqCapacity(), 0);
        request.setAttribute("currentRequest", requests.get(0).getId());
        requests = redao.getRequestFromDB(-1,"","",-1,-1,0,-1);

        request.setAttribute("sessionId", 100);
        request.setAttribute("type","Manager");
        request.setAttribute("routeArr", routes);
        request.setAttribute("reqArr", requests);
        request.setAttribute("cars", cars);

        System.out.println(cars.size()+ " cars sent");


    }
    public static void assignDriver(HttpServletRequest request) throws SQLException{
        boolean flag = false;
        RouteDAO rdao =(RouteDAO) BeanFactory.getBean(RouteDAO.class);
        List<Route> routes = rdao.getRouteFromDB(-1,"","");
        RequestDAO redao =(RequestDAO) BeanFactory.getBean(RequestDAO.class);
        List<Request> requests = redao.getRequestFromDB(Integer.parseInt(Service.getXSSSecureParameter(request.getParameter("reqID"))),"","",-1,-1,0,-1);
        CarDAO cdao = (CarDAO) BeanFactory.getBean(CarDAO.class);
        List<Car> cars = cdao.getCarFromDB(-1, "", "",-1, requests.get(0).getReqCapacity(), 0);

        for(Car a:cars){
            if(Integer.toString(a.getId()).equals( Service.getXSSSecureParameter(request.getParameter("selectedDriver")))){
                flag=true;
                break;
            }
        }
        if(flag) {
            redao.updateRequestAssignment(Service.getXSSSecureParameter(request.getParameter("selectedDriver")), Service.getXSSSecureParameter(request.getParameter("reqID")));
        }
        requests = redao.getRequestFromDB(-1,"","",-1,-1,0,-1);

        request.setAttribute("sessionId", 100);
        request.setAttribute("type","Manager");
        request.setAttribute("routeArr", routes);
        request.setAttribute("reqArr", requests);
        //request.setAttribute("cars", cars);

        System.out.println(cars.size()+ " cars sent");


    }
}
