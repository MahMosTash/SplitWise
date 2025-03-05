package models;

import models.enums.Menu;

import java.util.ArrayList;

public class App {
    private static final ArrayList<User> users = new ArrayList<>();
    private static final ArrayList<Group> groups = new ArrayList<>();
    private static Menu currentMenu = Menu.SignUpMenu;
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
    public static ArrayList<Group> getGroups() {
        return groups;
    }
    public static void addGroup(Group group) {
        groups.add(group);
    }
    public static Group getGroupByName(String name) {
        for(Group group : groups) {
            if(group.getName().equals(name)) {
                return group;
            }
        }
        return null;
    }
    public static Menu getCurrentMenu() {
        return currentMenu;
    }
    public static User getUserByUsername(String username) {
        for(User user : users) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public static int getGroupId() {
        return groups.size() + 1;
    }

    public static Group getGroupById(int groupId) {
        for(Group group : groups) {
            if(group.getId() == groupId) {
                return group;
            }
        }
        return null;
    }
}
