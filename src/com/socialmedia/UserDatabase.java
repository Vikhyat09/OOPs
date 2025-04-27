package com.socialmedia;

import java.util.ArrayList;
// stores data of all the users in the database
public class UserDatabase {
    private static ArrayList<User> users = new ArrayList<>();

    public static void addUser(User user) {
        users.add(user);
    }

    public static ArrayList<User> getAllUsers() {
        return users;
    }
}
