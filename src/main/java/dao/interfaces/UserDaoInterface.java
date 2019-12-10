package dao.interfaces;

import dao.bean.User;
import exceptions.UserNameHasExistedException;

import java.sql.SQLException;

public interface UserDaoInterface {
    /**
     * Add a new user to Db
     *
     * @param user User's Info (id is not needed)
     * @return the new user's Id
     */
    void addUser(User user) throws UserNameHasExistedException;

    /**
     * @param id
     * @return
     * @throws SQLException
     */
    User getUserById(long id);

    /**
     * @param name
     * @return
     */
    User getUserByName(String name);

    /**
     * @param user
     */
    void modifyUserProfile(User user);

    /**
     * update the the last login time
     * @param id user's id
     */
    void updateLastLoginTime(long id);
}
