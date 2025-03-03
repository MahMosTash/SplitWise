package models;

import models.enums.GroupType;

import java.util.ArrayList;


public class Group {
    private final String name;
    private final GroupType type;
    private final User creator;
    private final ArrayList<User> members = new ArrayList<>();
    private final ArrayList<Expense> expenses = new ArrayList<>();
    public Group(String name, String type, User creator) {
        this.name = name;
        this.type = GroupType.getGroupType(type);
        this.creator = creator;

        members.add(creator);
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
}
