package service;

import dao.UserDAO;
import model.UserAccount;

import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public void addUser(UserAccount user) {
        userDAO.insertUser(user);
    }

    public List<UserAccount> getUsers() {
        return userDAO.getAllUsers();
    }

    public void updateUser(UserAccount user) {
        userDAO.updateUser(user);
    }

    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }
}
