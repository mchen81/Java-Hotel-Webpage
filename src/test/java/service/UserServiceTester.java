package service;

import controller.servlets.user.UserBo;
import dao.bean.User;
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
            User user1 = userService.login("User09999", "x1234");
        } catch (Exception e) {
            System.out.println(e);
            Assert.fail("Login fail");
        }
    }

    @Test
    public void addNewUserTest() throws Exception {

        UserBo userBo = new UserBo();
        userBo.setUsername("User09999");
        userBo.setPassword("x1234");
        userBo.setEmail("bnb1083@gmail.com");
        userService.register(userBo);

    }
}
