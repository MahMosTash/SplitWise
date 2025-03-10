package models;

import models.enums.GroupType;

import java.util.ArrayList;


public class Group {
    private final String name;
    private final int id;
    private final GroupType type;
    private final User creator;
    private final ArrayList<User> members = new ArrayList<>();
    private final ArrayList<Expense> expenses = new ArrayList<>();
    public Group(String name, String type, User creator) {
        this.name = name;
        this.type = GroupType.getGroupType(type);
        this.creator = creator;
        this.id = App.getGroupId();
        members.add(creator);
        App.addGroup(this);
    }

    public String getName() {
        return name;
    }

    public GroupType getType() {
        return type;
    }

    public User getCreator() {
        return creator;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }
    public Expense getExpense(User paidBy, User paidFor) {
        for(Expense expense : expenses) {
            if(expense.getPaidBy().equals(paidBy) && expense.getPaidFor().equals(paidFor)) {
                return expense;
            } else if(expense.getPaidBy().equals(paidFor) && expense.getPaidFor().equals(paidBy)) {
                return expense;
            }
        }
        return null;
    }
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }
    public boolean isUserInGroup(User user) {
        for(User member : members) {
            if(member.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public int getId() {
        return id;
    }
    public void removeExpense(User paidBy, User paidFor) {
        for(Expense expense : expenses) {
            if(expense.getPaidBy().equals(paidBy) && expense.getPaidFor().equals(paidFor)) {
                expenses.remove(expense);
                paidBy.removeDebt(expense);
                paidFor.removeDemand(expense);
                break;
            }
        }
    }

}
