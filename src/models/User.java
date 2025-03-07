package models;

import models.enums.Currency;
import java.util.ArrayList;


public class User {
    private final String name;
    private String username;
    private final String email;
    private String password;
    private Currency currency = Currency.GTC;
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = Currency.getCurrency(currency);
    }

    public ArrayList<Group> getGroupsWith(User user) {
        ArrayList<Group> groups = new ArrayList<>();
        for(Group group : this.groups) {
            if(group.isUserInGroup(user)) {
                groups.add(group);
            }
        }
        return groups;
    }
    public void addExpenseToDemands(Expense expense) {
        demands.add(expense);
    }
    public void addExpenseToDebts(Expense expense) {
        debts.add(expense);
    }
    public ArrayList<Expense> getDemands() {
        return demands;
    }
    public ArrayList<Expense> getDebts() {
        return debts;
    }
}
