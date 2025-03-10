package controllers;

import models.*;
import models.enums.Currency;
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
            groups.append("id : ").append(group.getId()).append("\n");
            groups.append("type : ").append(GroupType.getGroupType(group.getType())).append("\n");
            groups.append("creator : ").append(group.getCreator().getName()).append("\n");
            groups.append("members : ").append("\n");
            for (User member : group.getMembers()) {
                groups.append(member.getName()).append("\n");
            }
            groups.append("--------------------");
        }
        return new Result(true, groups.toString());
    }

    public static Result addUser(String username, String email, int id) {
        User user = App.getUserByUsername(username);
        if(user == null) {
            return new Result(false, "user not found!");
        }
        Group group = App.getGroupById(id);
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
    public static Result addExpense(String split, int groupId, String totalExpense, ArrayList<Map<String, String>> users_expenses) {
        Group group = App.getGroupById(groupId);
        if(group == null) {
            return new Result(false, "group not found!");
        }
        if(!group.isUserInGroup(App.getLoggedInUser())) {
            return new Result(false, "you are not a member of this group!");
        }
        Result  usernamesValidation = validateUsernames(users_expenses, group);
        if(!usernamesValidation.success()) {
            return usernamesValidation;
        }
        if(!validateExpenses(users_expenses)) {
            return new Result(false, "expense format is invalid!");
        }
        if(DashboardCommands.UNEQUAL.matches(split) && isSumValid(users_expenses, totalExpense)) {
            return new Result(false, "the sum of individual costs does not equal the total cost!");
        }
        for(Map<String, String> user_expense : users_expenses) {
            String username = user_expense.keySet().iterator().next();
            int expense = Integer.parseInt(user_expense.get(username));
            User user = App.getUserByUsername(username);
            if(!DashboardCommands.UNEQUAL.matches(split)) {

                expense = Integer.parseInt(totalExpense);
                expense /= users_expenses.size();

            }
            createExpense(App.getLoggedInUser(),user, expense, group);
        }
        return new Result(true, "expense added successfully!");
    }

    private static Result validateUsernames(ArrayList<Map<String, String>> users_expenses, Group group) {
        for(Map<String, String> user_expense : users_expenses) {
            String username = user_expense.keySet().iterator().next();
            User user = App.getUserByUsername(username);
            if(user == null) {
                return new Result(false, username + " not in group!");
            }
            if(!group.isUserInGroup(user)) {
                return new Result(false, username + " not in group!");
            }
        }
        return new Result(true, "usernames are valid!");
    }
    private static boolean validateExpenses(ArrayList<Map<String, String>> users_expenses) {
        for(Map<String, String> user_expense : users_expenses) {
            String expense = user_expense.values().iterator().next();
            if(!DashboardCommands.EXPENSE.matches(expense)) {
                return false;
            }
        }
        return true;
    }
    private static boolean isSumValid(ArrayList<Map<String, String>> users_expenses, String totalExpense) {
        int sum = 0;
        for(Map<String, String> user_expense : users_expenses) {
            sum += Integer.parseInt(user_expense.values().iterator().next());
        }
        return sum != Integer.parseInt(totalExpense);
    }

    private static void createExpense(User paidBy, User paidFor, int amount, Group group) {
        if(amount == 0) {
            return;
        }
        Expense expense = group.getExpense(paidBy, paidFor);
        if(expense == null) {
            expense = group.getExpense(paidFor, paidBy);
            if(expense == null) {
                new Expense(App.getLoggedInUser().getCurrency(), amount, paidBy, paidFor, group);
            } else {
                amount = (int) App.getLoggedInUser().getCurrency().convertTo(expense.getCurrency(), amount);
                int totalAmount = expense.getAmount() - amount;
                if(totalAmount == 0) {
                    group.removeExpense(paidFor, paidBy);
                } else if(totalAmount < 0) {
                    expense.setAmount(-totalAmount);
                    expense.changePaidByWithPaidFor();
                } else {
                    expense.setAmount(totalAmount);
                }
            }
        } else {
            expense.setAmount(expense.getAmount() + amount);
        }
        explainWhatTheFuckIsGoingOn(paidBy, paidFor, amount, group);
    }

    private static void explainWhatTheFuckIsGoingOn(User paidBy, User paidFor, int amount, Group group) {

    }


    public static Result getShowBalance(String username) {
        User user = App.getUserByUsername(username);
        if(user == null) {
            return new Result(false, "user not found!");
        }

        StringBuilder balance = new StringBuilder();
        int check = getBalance(App.getLoggedInUser(), user);
        if(check < 0) {
            balance.append("you owe ").append(username).append(" ").append(-check).append(" ").append(App.getLoggedInUser().getCurrency().getCurrency());
        } else if(check > 0) {
            balance.append(username).append(" owes you ").append(check).append(" ").append(App.getLoggedInUser().getCurrency().getCurrency());
        } else {
            balance.append("you are settled with ").append(username);
            return new Result(true, balance.toString());
        }
        balance.append(" in ");
        ArrayList<Group> groups = getCommonGroups(App.getLoggedInUser(), user);
        for(int i = 0; i < groups.size(); i++) {
            balance.append(groups.get(i).getName());
            if(i != groups.size() - 1) {
                balance.append(", ");
            }

        }
        balance.append("!");
        return new Result(true, balance.toString());
    }

    public static Result settleUp(String username) {
        User user = App.getUserByUsername(username);
        if(user == null) {
            return new Result(false, "user not found!");
        }
        int balance = getBalance(App.getLoggedInUser(), user);
        ArrayList<Group> groups = getCommonGroups(App.getLoggedInUser(), user);
        settleUpGroups(groups, user, balance);
        if(balance == 0) {
            return new Result(true, "you are settled with " + username + " now!");
        } else if(balance < 0) {
            return new Result(true, "you owe " + username + " " + -balance + " " + App.getLoggedInUser().getCurrency().getCurrency() + " now!");
        } else {
            return new Result(true, username + " owes you " + balance + " " + App.getLoggedInUser().getCurrency().getCurrency() + " now!");
        }
    }

    public static void settleUpGroups(ArrayList<Group> groups, User user, int balance) {

        for(Group group : groups) {
            group.removeExpense(App.getLoggedInUser(), user);
        }
        Group lastGroup = groups.getLast();
        if(balance == 0) {
            return;
        }

        if(balance < 0) {
             new Expense(App.getLoggedInUser().getCurrency(), -balance, App.getLoggedInUser(), user, lastGroup);
        } else {
            new Expense(App.getLoggedInUser().getCurrency(), balance, user, App.getLoggedInUser(), lastGroup);
        }
    }

    public static int getBalance(User user1, User user2) {
        int debt = 0;
        int demand = 0;
        for(Expense expense : user1.getDebts()) {
            if(expense.getPaidBy().equals(user1) && expense.getPaidFor().equals(user2)) {
                debt += (int) expense.getCurrency().convertTo(App.getLoggedInUser().getCurrency(), expense.getAmount());
            }
        }
        for(Expense expense : user1.getDemands()) {
            if(expense.getPaidFor().equals(user1) && expense.getPaidBy().equals(user2)) {
                demand += (int) expense.getCurrency().convertTo(App.getLoggedInUser().getCurrency(), expense.getAmount());
            }
        }

        return debt - demand;
    }

    public static ArrayList<Group> getCommonGroups(User user1, User user2) {
        ArrayList<Group> commonGroups = new ArrayList<>();
        for(Group group : user1.getGroups()) {
            if(user2.getGroups().contains(group)) {
                commonGroups.add(group);
            }
        }
        return commonGroups;
    }

}

