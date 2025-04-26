package com.socialmedia;

import java.util.ArrayList;

public class LoginManager {
    /**
     * Authenticates a user by matching the username and password against the list of users.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @param users    The list of all registered users in the system.
     * @return The authenticated User object if the login is successful, or null if authentication fails.
     */
    public static User authenticate(String username, String password, ArrayList<User> users) {
        for (User user : users) {
            // Correct comparison of username and password
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user; // Return the authenticated user
            }
        }
        return null; // Authentication failed
    }
}