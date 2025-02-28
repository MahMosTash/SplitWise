package controllers;

import models.*;
import models.enums.DashboardCommands;
import models.enums.GroupType;

import java.util.ArrayList;

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
    public static Result addExpense(String groupName, int totalExpense, ArrayList<Expense> expenses) {
        // TODO: implement this method
        return new Result(true, "expense added successfully!");
    }

}

