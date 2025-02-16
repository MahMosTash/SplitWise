package models;

import models.enums.Menu;

import java.util.ArrayList;

public class App {
    private static final ArrayList<User> users = new ArrayList<>();
    private static Menu currentMenu = Menu.LoginMenu;
    private static User loggedInUser;
    public static void setCurrentMenu(Menu currentMenu) {
        App.currentMenu = currentMenu;
    }
    public static ArrayList<User> getUsers() {
        return users;
    }
    public static User getLoggedInUser() {
        return loggedInUser;
    }
    public static void setLoggedInUser(User loggedInUser) {
        App.loggedInUser = loggedInUser;
    }
    public static void addUser(User user){
        users.add(user);
    }
    public static Menu getCurrentMenu() {
        return currentMenu;
    }
}
