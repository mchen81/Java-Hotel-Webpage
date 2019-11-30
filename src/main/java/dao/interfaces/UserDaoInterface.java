package dao.interfaces;

import dao.bean.User;

import java.sql.SQLException;

public interface UserDaoInterface {
    /**
     * Add a new user to Db
     *
     * @param user User's Info (id is not needed)
     * @return the new user's Id
     * @throws SQLException error code would be 1062, duplicate entry
     */
    long addUser(User user) throws SQLException;

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
}
