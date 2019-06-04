package com.mishon.couriers;
import com.mishon.couriers.DAO.*;

import java.util.HashMap;

import java.util.Map;
public class BeanFactory {




    private static Map<Class<?>, Object> beans = new HashMap<>();




    static {

        ArchiveDAO archiveDAO = new ArchiveDAO();

        beans.put(ArchiveDAO.class, archiveDAO);




        CarDAO carDAO = new CarDAO();

        beans.put(CarDAO.class, carDAO);




        RequestDAO requestDAO = new RequestDAO();

        beans.put(RequestDAO.class, requestDAO);




        RouteDAO routeDAO = new RouteDAO();

        beans.put(RouteDAO.class, routeDAO);

        UserDAO userDAO = new UserDAO();

        beans.put(UserDAO.class, userDAO);


    }




    public static Object getBean(Class<?> beanClass) {

        return beans.get(beanClass);

    }




}
