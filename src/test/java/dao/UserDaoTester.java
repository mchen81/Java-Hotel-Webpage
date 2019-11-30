package dao;

import dao.bean.User;
import dao.interfaces.UserDaoInterface;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class UserDaoTester {

    private UserDaoInterface userDao;

    @Before
    public void setUP() {
        userDao = new UserDao();
    }

    @Test
    public void addUserTest() {
        try {
            User newUser = new User();
            newUser.setName("Jerry199998");
            newUser.setKey("0000");
            newUser.setEmailAddress("default@default.com");
            userDao.addUser(newUser);
        } catch (SQLException e) {
            Assert.assertEquals(1062, e.getErrorCode());
        }
    }

    @Test
    public void getUserByIdTest() {
        User user = userDao.getUserById(1);
        Assert.assertEquals("Anonymous", user.getName());
        Assert.assertEquals("0000", user.getKey());
        Assert.assertEquals("defalut@default.com", user.getEmailAddress());
    }

    @Test
    public void getUserByNameTest() {
        User user = userDao.getUserByName("J");
        Assert.assertEquals(13, user.getId());
        Assert.assertEquals("J", user.getName());
        Assert.assertEquals("0000", user.getKey());
        Assert.assertEquals("defalut@default.com", user.getEmailAddress());
    }

    @Test
    public void modifyUser() {
        String newPassword = "1111";
        String newEmail = "new@email.com";
        User user = new User();
        user.setId(747);
        user.setName("Jerry2222");// not change
        user.setKey(newPassword);
        user.setEmailAddress(newEmail);
        userDao.modifyUserProfile(user);
        User user2 = userDao.getUserByName("Jerry");
        Assert.assertEquals(newPassword, user2.getKey());
        Assert.assertEquals(newEmail, user2.getEmailAddress());
    }
}
