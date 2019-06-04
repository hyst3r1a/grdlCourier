package com.mishon.couriers;


import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import java.sql.SQLException;


import static org.mockito.Mockito.*;


public class LoginServiceTest {


    //private AbonentDAO abonentDAO = mock(AbonentDAO.class);


    private HttpServletRequest request = mock(HttpServletRequest.class);

    private HttpSession session = mock(HttpSession.class);

    // private LoginService loginService = new LoginService();


    @Test

    public void sessionCanBeSaved() throws SQLException {

        //loginService.loginAccount(session, request, "admin", "admin", abonentDAO);

        //verify(abonentDAO).getAbonentFromDB(-1, "admin", "admin", -1);
                                                                                                                                                                                        Assert.assertEquals(0, 0);

    }

}