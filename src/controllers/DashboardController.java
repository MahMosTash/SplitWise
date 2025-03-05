package controllers;

import models.*;
import models.enums.DashboardCommands;
import models.enums.GroupType;

import java.util.*;

public class DashboardController {
    public static Result createGroup(String name, String type) {
        if(!DashboardCommands.NAME.matches(name)) {
            return new Result(false, "group name format is invalid!");
        } else if(!DashboardCommands.TYPE.matches(type)) {
            return new Result(false, "group type is invalid!");
        }
        // create group
        Group group = new Group(name, type, App.getLoggedInUser());
        App.getLoggedInUser().addGroup(group);
        return new Result(true, "group created successfully!");
    }
    public static Result showMyGroups() {
        StringBuilder groups = new StringBuilder();
        for(Group group : App.getLoggedInUser().getGroups()) {
            groups.append("group name : ").append(group.getName()).append("\n");
            groups.append("type : ").append(GroupType.getGroupType(group.getType())).append("\n");
            groups.append("creator : ").append(group.getCreator().getName()).append("\n");
            groups.append("members : ").append("\n");
            for (User member : group.getMembers()) {
                groups.append(member.getName()).append("\n");
            }
        }
        return new Result(true, groups.toString());
    }

    public static Result addUser(String username, String email, String groupName ) {
        User user = App.getUserByUsername(username);
        if(user == null) {
            return new Result(false, "user not found!");
        }
        Group group = App.getGroupByName(groupName);
        if(group == null) {
            return new Result(false, "group not found!");
        }
        if(group.isUserInGroup(user)) {
            return new Result(false, "user already in the group!");
        }
        if(!user.getEmail().equals(email)) {
            return new Result(false, "the email provided does not match the username!");
        }
        if(!group.getCreator().equals(App.getLoggedInUser())) {
            return new Result(false, "only the group creator can add users!");
        }
        group.addMember(user);
        user.addGroup(group);
        return new Result(true, "user added to the group successfully!");
    }
    public static Result addExpense(String split, int groupId, String totalExpense, String usernames) {
        if(DashboardCommands.EQUAL.matches(split)) {
            return handleAddExpenseEqually(groupId, totalExpense, usernames);
        }
        return handleAddExpenseUnequally(groupId, totalExpense, usernames);
    }

    private static Result handleAddExpenseUnequally(int groupId, String totalExpense, String usernames) {
        Group group = App.getGroupById(groupId);
        if(group == null) {
            return new Result(false, "group not found!");
        }
        if(!group.isUserInGroup(App.getLoggedInUser())) {
            return new Result(false, "you are not a member of this group!");
        }
        Result invalidUser = findInvalidUserUnequally(usernames);
        if(invalidUser.success()) {
            return invalidUser;
        }
        if(!DashboardCommands.EXPENSE.matches(totalExpense)) {
            return new Result(false, "expense format is invalid!");
        }
        int totalExpense_ = Integer.parseInt(totalExpense);

        Map<User, Integer> users = getUserForAddExpenseUnequally(usernames);
        if(!isValidTotalExpense(totalExpense_, users)) {
            return new Result(false, "the sum of individual costs does not equal the total cost!");
        }
        createUnequallyExpenses(group,totalExpense_, users);
        return new Result(true, "expense added successfully!");
    }

    private static boolean isValidTotalExpense(int totalExpense, Map<User, Integer> users) {
        int sum = 0;
        for(Map.Entry<User, Integer> entry: users.entrySet()) {
            sum += entry.getValue();
        }
        return sum == totalExpense;
    }

    private static void createUnequallyExpenses(Group group, int totalExpense, Map<User, Integer> usersAndAmount) {
        for(Map.Entry<User, Integer> entry: usersAndAmount.entrySet()) {
            Expense expense = new Expense(App.getLoggedInUser().getCurrency(), entry.getValue(), App.getLoggedInUser(), entry.getKey(), group);
        }

    }

    private static Map<User, Integer> getUserForAddExpenseUnequally(String usernames) {
        String[] list = usernames.split("\\s*,\\s*");
        Map<User, Integer> users = new HashMap<>();
        for(String username: list) {
            String[] userAndAmount = username.split("\\s*:\\s*");
            users.put(App.getUserByUsername(userAndAmount[0]), Integer.parseInt(userAndAmount[1]));
        }
        return users;
    }
    public static Result findInvalidUserUnequally(String usernames) {
        String[] list = usernames.split("\\s*,\\s*");
        for(String username: list) {
            String[] userAndAmount = username.split("\\s*:\\s*");
            if(App.getUserByUsername(userAndAmount[0]) == null) {
                return new Result(false, userAndAmount[0] + " not in group!");
            }
        }
        return new Result(true, "");
    }
    public static Result handleAddExpenseEqually(int groupId, String totalExpense, String usernames) {
        Group group = App.getGroupById(groupId);
        if(group == null) {
            return new Result(false, "group not found!");
        }
        if(!group.isUserInGroup(App.getLoggedInUser())) {
            return new Result(false, "you are not a member of this group!");
        }
        Result invalidUser = findInvalidUser(usernames);
        if(invalidUser.success()) {
            return invalidUser;
        }
        if(!DashboardCommands.EXPENSE.matches(totalExpense)) {
            return new Result(false, "expense format is invalid!");
        }
        ArrayList<User> users = getUserForAddExpenseEqually(usernames);
        createEquallyExpenses(group, Double.parseDouble(totalExpense), users);
        return new Result(true, "expense added successfully!");
    }
    private static ArrayList<User> getUserForAddExpenseEqually(String usernames) {
        String[] usernameList = usernames.split("\\s*,\\s*");
        ArrayList<User> users = new ArrayList<>();
        for(String username: usernameList) {
            users.add(App.getUserByUsername(username));
        }
        return users;
    }
    private static void createEquallyExpenses(Group group, double totalExpense, ArrayList<User> users) {
        int expensePerUser = (int) totalExpense / users.size();
        for(User user: users) {
            Expense expense = new Expense(App.getLoggedInUser().getCurrency(), expensePerUser, App.getLoggedInUser(), user, group);
        }
    }
    private static Result findInvalidUser(String usernames) {
        String[] usernameList = usernames.split("\\s*,\\s*");
        for(String username: usernameList) {
            if(App.getUserByUsername(username) == null) {
                return new Result(false, username + " not in group!");
            }
        }
        return new Result(true, "");
    }
    public static Result getShowBalance(String username) {
        User user = App.getUserByUsername(username);
        if(user == null) {
            return new Result(false, "user not found!");
        }
        int debt = 0;
        int demand = 0;
        StringBuilder balance = new StringBuilder();

        ArrayList<Group> groups = new ArrayList<>();
        for(Expense expense : user.getDebts()) {
            if(expense.paidFor().equals(user)) {
                debt += expense.amount();
                groups.add(expense.group());
            }
        }
        for(Expense expense : user.getDemands()) {
            if(expense.paidBy().equals(user)) {
                demand += expense.amount();
                groups.add(expense.group());
            }
        }
        if(debt > demand) {
            balance.append("you owe ").append(username).append(" ").append(debt - demand);
        } else if(debt < demand) {
            balance.append(username).append(" owes you ").append(demand - debt);
        } else {
            balance.append("you are settled with ");
        }
        balance.append(" in ");
        for(int i = 0; i < groups.size(); i++) {
            balance.append(groups.get(i).getName());
            if(i != groups.size() - 1) {
                balance.append(", ");
            } else {
                balance.append("!");
            }
        }
        return new Result(true, balance.toString());
    }


}

