package com.mishon.couriers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class Service {
    final static Logger logger = LogManager.getLogger("com.zetcode");
    public static void autentificate(HttpServletRequest request){
        RequestDAO redao = new RequestDAO();
        ArrayList<Request> requests = redao.getRequestFromDB(-1,"","",-1,-1,0,-1);

        UserDAO udao = new UserDAO();
        ArrayList<User> users = udao.getUserFromDB(-1,"","","");

        RouteDAO rdao = new RouteDAO();
        ArrayList<Route> routes = rdao.getRouteFromDB(-1,"","");

        CarDAO cdao = new CarDAO();

        request.setAttribute("sessionId", 0);

        String pass = getXSSSecureParameter(request.getParameter("pass"));
        if(pass.equals("")){
            pass = ",";
        }
        users = udao.getUserFromDB(-1, getXSSSecureParameter(request.getParameter("login")),"", pass);


        if(users.size() >0) {
            if (users.get(0) != null) {
                ArrayList<Car> cars = cdao.getCarFromDB(-1, "", "", users.get(0).getId(), -1, -1);
                if (users.get(0).getType().equals("Driver")) {
                    driverGetRequests(request, users.get(0).getId());
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
    public static void insertRequest(HttpServletRequest request){
        RequestDAO redao = new RequestDAO();


        RouteDAO rdao = new RouteDAO();
        ArrayList<Route> routes = rdao.getRouteFromDB(-1,"","");
        // if(Integer.parseInt(getXSSSecureParameter(request.getParameter("route")))<routes.size()) {
        redao.addRequestToDB(getXSSSecureParameter(request.getParameter("issuer")),
                getXSSSecureParameter(request.getParameter("date")),
                getXSSSecureParameter(request.getParameter("req_capacity")),
                getXSSSecureParameter(request.getParameter("price")), "0",
                getXSSSecureParameter(request.getParameter("route")));

        //  }
        ArrayList<Request> requests = redao.getRequestFromDB(-1,"","",-1,-1,0,-1);

        request.setAttribute("sessionId", 100);
        request.setAttribute("type","Manager");
        request.setAttribute("routeArr", routes);
        request.setAttribute("reqArr", requests);
    }
    public static void getSuitableDrivers(HttpServletRequest request){
        RouteDAO rdao = new RouteDAO();
        ArrayList<Route> routes = rdao.getRouteFromDB(-1,"","");
        RequestDAO redao = new RequestDAO();
        ArrayList<Request> requests = redao.getRequestFromDB(Integer.parseInt(getXSSSecureParameter(request.getParameter("reqID"))),"","",-1,-1,0,-1);
        CarDAO cdao = new CarDAO();
        ArrayList<Car> cars = cdao.getCarFromDB(-1, "", "",-1, requests.get(0).getReqCapacity(), 0);
        request.setAttribute("currentRequest", requests.get(0).id);
        requests = redao.getRequestFromDB(-1,"","",-1,-1,0,-1);

        request.setAttribute("sessionId", 100);
        request.setAttribute("type","Manager");
        request.setAttribute("routeArr", routes);
        request.setAttribute("reqArr", requests);
        request.setAttribute("cars", cars);

        System.out.println(cars.size()+ " cars sent");


    }
    public static void assignDriver(HttpServletRequest request){
        boolean flag = false;
        RouteDAO rdao = new RouteDAO();
        ArrayList<Route> routes = rdao.getRouteFromDB(-1,"","");
        RequestDAO redao = new RequestDAO();
        ArrayList<Request> requests = redao.getRequestFromDB(Integer.parseInt(getXSSSecureParameter(request.getParameter("reqID"))),"","",-1,-1,0,-1);
        CarDAO cdao = new CarDAO();
        ArrayList<Car> cars = cdao.getCarFromDB(-1, "", "",-1, requests.get(0).getReqCapacity(), 0);

        for(Car a:cars){
            if(Integer.toString(a.getId()).equals( getXSSSecureParameter(request.getParameter("selectedDriver")))){
                flag=true;
                break;
            }
        }
        if(flag) {
            redao.updateRequestAssignment(getXSSSecureParameter(request.getParameter("selectedDriver")), getXSSSecureParameter(request.getParameter("reqID")));
        }
        requests = redao.getRequestFromDB(-1,"","",-1,-1,0,-1);

        request.setAttribute("sessionId", 100);
        request.setAttribute("type","Manager");
        request.setAttribute("routeArr", routes);
        request.setAttribute("reqArr", requests);
        //request.setAttribute("cars", cars);

        System.out.println(cars.size()+ " cars sent");


    }
    public static void driverCompleteRequest(HttpServletRequest request){
        CarDAO cdao = new CarDAO();
        ArrayList<Car> cars = cdao.getCarFromDB(-1, "", "",
                Integer.parseInt(getXSSSecureParameter(request.getParameter("currentUser"))), -1, -1);
        RequestDAO redao = new RequestDAO();
        ArrayList<Request> requests = redao.getRequestFromDB(Integer.parseInt(getXSSSecureParameter(request.getParameter("reqID"))),
                "","",-1,-1,cars.get(0).getId(),-1);
        ArchiveDAO adao = new ArchiveDAO();
        ArrayList<Archive> archives = adao.getArchiveFromDB(-1, -1, -1,-1, "", -1, "");

        adao.addArchiveToDB(Integer.toString(requests.get(0).getRoute()), Integer.toString(cars.get(0).getId()), Integer.toString(requests.get(0).getPrice()),requests.get(0).getDate(),
                getXSSSecureParameter(request.getParameter("expense")),getXSSSecureParameter(request.getParameter("completed") ));
        redao.removeRequest(getXSSSecureParameter(request.getParameter("reqID")));
        cdao.breakCar(cars.get(0).getId(), Integer.parseInt(getXSSSecureParameter(request.getParameter("expense"))));
        requests = redao.getRequestFromDB(-1,"","",-1,-1,cars.get(0).getId(),-1);

        cars = cdao.getCarFromDB(-1, "", "",Integer.parseInt(getXSSSecureParameter(request.getParameter("currentUser"))), -1, -1);

        request.setAttribute("sessionId", 100);
        request.setAttribute("type","Driver");
        request.setAttribute("currentPrice",cars.get(0).getNeedRepairs());
        request.setAttribute("currentUser", getXSSSecureParameter(request.getParameter("currentUser")));
        request.setAttribute("reqArr", requests);
    }
    public static void driverGetRequests(HttpServletRequest request, int userId){
        CarDAO cdao = new CarDAO();
        ArrayList<Car> cars = cdao.getCarFromDB(-1, "", "",userId, -1, -1);
        RequestDAO redao = new RequestDAO();
        ArrayList<Request> requests = redao.getRequestFromDB(-1,"","",-1,-1,cars.get(0).getId(),-1);

        request.setAttribute("sessionId", 100);
        request.setAttribute("type","Driver");
        request.setAttribute("currentPrice",cars.get(0).getNeedRepairs());
        request.setAttribute("currentUser", userId);
        request.setAttribute("reqArr", requests);
    }
    public static void driverFixCar(HttpServletRequest request){
        CarDAO cdao = new CarDAO();
        ArrayList<Car> cars = cdao.getCarFromDB(-1, "", "",Integer.parseInt(request.getParameter("currentUser")), -1, -1);

        RequestDAO redao = new RequestDAO();
        ArrayList<Request> requests = redao.getRequestFromDB(-1,"","",-1,-1,cars.get(0).getId(),-1);


        cdao.fixCar(cars.get(0).getId());

        cars = cdao.getCarFromDB(-1, "", "",Integer.parseInt(getXSSSecureParameter(request.getParameter("currentUser"))), -1, -1);

        request.setAttribute("sessionId", 100);
        request.setAttribute("type","Driver");
        request.setAttribute("currentPrice",cars.get(0).getNeedRepairs());
        request.setAttribute("currentUser", getXSSSecureParameter(request.getParameter("currentUser")));
        request.setAttribute("reqArr", requests);
    }
    public static String getXSSSecureParameter(String s) {
        return s.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;").
                replaceAll("'", "&apos;").replaceAll("&", "&amp;");
    }
}
