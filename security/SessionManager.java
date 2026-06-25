package security;

import model.UserAccount;

public class SessionManager {
    private static UserAccount currentUser;

    public static void setCurrentUser(UserAccount user) {
        currentUser = user;
    }

    public static UserAccount getCurrentUser() {
        return currentUser;
    }

    public static void clear() {
        currentUser = null;
    }

    public static boolean isAdmin() {
        return currentUser != null
                && "ADMIN".equalsIgnoreCase(currentUser.getRole());
    }
}