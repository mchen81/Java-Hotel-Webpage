package dao;

import dao.bean.User;
import dao.interfaces.UserDaoInterface;
import exceptions.UserNameHasExistedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserDaoTester {

    private UserDaoInterface userDao;

    @Before
    public void setUp() {
        userDao = new UserDao();
    }

    @Test
    public void addUserTest() {
        User newUser = new User();
        newUser.setName("Jerry199998");
        newUser.setHashPass("0000");
        newUser.setEmailAddress("default@default.com");
        try {
            userDao.addUser(newUser);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof UserNameHasExistedException);
        }
    }

    @Test
    public void getUserByIdTest() {
        User user = userDao.getUserById(1);
        Assert.assertEquals("Anonymous", user.getName());
        Assert.assertEquals("0000", user.getHashPass());
        Assert.assertEquals("defalut@default.com", user.getEmailAddress());
    }

    @Test
    public void getUserByNameTest() {
        User user = userDao.getUserByName("J");
        Assert.assertEquals(13, user.getId());
        Assert.assertEquals("J", user.getName());
        Assert.assertEquals("0000", user.getHashPass());
        Assert.assertEquals("defalut@default.com", user.getEmailAddress());
    }

    @Test
    public void modifyUser() {
        String newPassword = "1111";
        String newEmail = "new@email.com";
        User user = new User();
        user.setId(747);
        user.setName("Jerry2222");// not change
        user.setHashPass(newPassword);
        user.setEmailAddress(newEmail);
        userDao.modifyUserProfile(user);
        User user2 = userDao.getUserByName("Jerry");
        Assert.assertEquals(newPassword, user2.getHashPass());
        Assert.assertEquals(newEmail, user2.getEmailAddress());
    }

    @Test
    public void updateLastLoginTimeTest() {
        userDao.updateLastLoginTime(2);
    }
}
