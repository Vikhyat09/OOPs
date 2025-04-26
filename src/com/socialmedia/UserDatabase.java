package com.socialmedia;

import java.util.ArrayList;

public class UserDatabase {
    private static ArrayList<User> users = new ArrayList<>();

    public static void addUser(User user) {
        users.add(user);
    }

    public static ArrayList<User> getAllUsers() {
        return users;
    }
}