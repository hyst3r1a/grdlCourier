package com.mishon.couriers.Services;

import com.mishon.couriers.BeanFactory;
import com.mishon.couriers.DAO.ArchiveDAO;
import com.mishon.couriers.DAO.CarDAO;
import com.mishon.couriers.Entities.Archive;
import com.mishon.couriers.Entities.Car;
import com.mishon.couriers.Entities.Request;
import com.mishon.couriers.DAO.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class DriverService {
    public static void driverCompleteRequest(HttpServletRequest request) throws SQLException {
        CarDAO cdao = (CarDAO) BeanFactory.getBean(CarDAO.class);
        List<Car> cars = cdao.getCarFromDB(-1, "", "",
                Integer.parseInt(Service.getXSSSecureParameter(request.getParameter("currentUser"))), -1, -1);
        RequestDAO redao =(RequestDAO) BeanFactory.getBean(RequestDAO.class);
        List<Request> requests = redao.getRequestFromDB(Integer.parseInt(Service.getXSSSecureParameter(request.getParameter("reqID"))),
                "","",-1,-1,cars.get(0).getId(),-1);
        ArchiveDAO adao =(ArchiveDAO) BeanFactory.getBean(ArchiveDAO.class);
        List<Archive> archives = adao.getArchiveFromDB(-1, -1, -1,-1, "", -1, "");

        adao.addArchiveToDB(Integer.toString(requests.get(0).getRoute()), Integer.toString(cars.get(0).getId()), Integer.toString(requests.get(0).getPrice()),requests.get(0).getDate(),
                Service.getXSSSecureParameter(request.getParameter("expense")),Service.getXSSSecureParameter(request.getParameter("completed") ));
        redao.removeRequest(Service.getXSSSecureParameter(request.getParameter("reqID")));
        cdao.breakCar(cars.get(0).getId(), Integer.parseInt(Service.getXSSSecureParameter(request.getParameter("expense"))));
        requests = redao.getRequestFromDB(-1,"","",-1,-1,cars.get(0).getId(),-1);

        cars = cdao.getCarFromDB(-1, "", "",Integer.parseInt(Service.getXSSSecureParameter(request.getParameter("currentUser"))), -1, -1);

        request.setAttribute("sessionId", 100);
        request.setAttribute("type","Driver");
        request.setAttribute("currentPrice",cars.get(0).getNeedRepairs());
        request.setAttribute("currentUser", Service.getXSSSecureParameter(request.getParameter("currentUser")));
        request.setAttribute("reqArr", requests);
    }
    public static void driverGetRequests(HttpServletRequest request, int userId) throws SQLException{
        CarDAO cdao = (CarDAO) BeanFactory.getBean(CarDAO.class);
        List<Car> cars = cdao.getCarFromDB(-1, "", "",userId, -1, -1);
        RequestDAO redao =(RequestDAO) BeanFactory.getBean(RequestDAO.class);
        List<Request> requests = redao.getRequestFromDB(-1,"","",-1,-1,cars.get(0).getId(),-1);

        request.setAttribute("sessionId", 100);
        request.setAttribute("type","Driver");
        request.setAttribute("currentPrice",cars.get(0).getNeedRepairs());
        request.setAttribute("currentUser", userId);
        request.setAttribute("reqArr", requests);
    }
    public static void driverFixCar(HttpServletRequest request) throws SQLException{
        CarDAO cdao =(CarDAO) BeanFactory.getBean(CarDAO.class);
        List<Car> cars = cdao.getCarFromDB(-1, "", "",Integer.parseInt(request.getParameter("currentUser")), -1, -1);

        RequestDAO redao = (RequestDAO) BeanFactory.getBean(RequestDAO.class);
        List<Request> requests = redao.getRequestFromDB(-1,"","",-1,-1,cars.get(0).getId(),-1);


        cdao.fixCar(cars.get(0).getId());

        cars = cdao.getCarFromDB(-1, "", "",Integer.parseInt(Service.getXSSSecureParameter(request.getParameter("currentUser"))), -1, -1);

        request.setAttribute("sessionId", 100);
        request.setAttribute("type","Driver");
        request.setAttribute("currentPrice",cars.get(0).getNeedRepairs());
        request.setAttribute("currentUser", Service.getXSSSecureParameter(request.getParameter("currentUser")));
        request.setAttribute("reqArr", requests);
    }
}
