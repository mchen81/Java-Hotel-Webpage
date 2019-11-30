package service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.interfaces.UserServiceInterface;

public class UserServiceTester {

    private UserServiceInterface userService;

    @Before
    public void setUp() {
        userService = ServicesSingleton.getUserService();
    }

    @Test
    public void loginTest() {
        try {
            long userId1 = userService.login("Lynda", "0000");
            Assert.assertEquals(2l, userId1);
            long userId2 = userService.login("Bill", "0000");
            Assert.assertEquals(37l, userId2);
        } catch (Exception e) {
            System.out.println(e);
            Assert.fail("Login fail");
        }
    }

    void loginFailTest() {
    }
}
