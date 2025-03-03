package models;

import java.util.ArrayList;

public class User {
    private final String name;
    private String username;
    private final String email;
    private String password;
    private String currency = "USD";
    private final ArrayList<User> friends = new ArrayList<>();
    private final ArrayList<Group> groups = new ArrayList<>();
    private final ArrayList<Expense> demands = new ArrayList<>();
    private final ArrayList<Expense> debts = new ArrayList<>();


    public User(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordCorrect(String password) {
        return password.equals(this.password);
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }
    public void addGroup(Group group) {
        groups.add(group);
    }

    public String getEmail() {
        return email;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
