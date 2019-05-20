package com.mishon.couriers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ServiceTest {

    ArchiveDAO adao;
    CarDAO cdao;
    RequestDAO redao;
    RouteDAO rdao;
    UserDAO udao;
    int testCount = 0;
    @Before
    public void setUp() throws Exception {
        adao = Mockito.mock(ArchiveDAO.class);
        cdao = Mockito.mock(CarDAO.class);
        redao = Mockito.mock(RequestDAO.class);
        rdao = Mockito.mock(RouteDAO.class);
        udao = Mockito.mock(UserDAO.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void autentificate() {
        Service.autentificate(Mockito.mock(HttpServletRequest.class));
        User a = new User();
        ArrayList<User> b = new ArrayList<>();
        b.add(a);
    Mockito.when(udao.getUserFromDB(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(b);
        Mockito.when(redao.getRequestFromDB(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt())).thenReturn(new ArrayList<Request>());

        Service.autentificate(Mockito.mock(HttpServletRequest.class));
    assertEquals(1, b.size());

    }

    @Test
    public void insertRequest() {
    }

    @Test
    public void getSuitableDrivers() {
    }

    @Test
    public void assignDriver() {
    }

    @Test
    public void driverCompleteRequest() {
    }

    @Test
    public void driverGetRequests() {
    }

    @Test
    public void driverFixCar() {
    }

    @Test
    public void getXSSSecureParameter() {
    }
}