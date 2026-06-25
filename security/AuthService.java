package security;

import dao.UserDAO;
import model.UserAccount;

import java.util.List;

public class AuthService {

    private UserDAO userDAO = new UserDAO();

    public UserAccount login(String username, String passwordRaw) {

        String hashed = PasswordUtils.hashPassword(passwordRaw);

        List<UserAccount> users = userDAO.getAllUsers();

        for (UserAccount u : users) {
            if (u.getUsername().equals(username)
                    && u.getPasswordHash().equals(hashed)) {
                return u;
            }
        }

        return null;
    }
}