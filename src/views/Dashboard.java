package views;

import controllers.DashboardController;
import models.App;
import models.Result;
import models.enums.DashboardCommands;
import models.enums.Menu;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Dashboard implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine().trim();
        if(DashboardCommands.CREATE_GROUP.matches(input)) {
            handleCreateGroup(input);
        } else if(DashboardCommands.EXIT.matches(input)) {
            App.setCurrentMenu(Menu.Exit);
        } else if(DashboardCommands.SHOW_MY_GROUPS.matches(input)) {
            Result result = DashboardController.showMyGroups();
            System.out.println(result.message());
        } else if(DashboardCommands.ADD_USER.matches(input)) {
            handleAddUser(input);
        } else if(DashboardCommands.ADD_EXPENSE.matches(input)) {
            handleAddExpense(input);
        } else if(DashboardCommands.SHOW_BALANCE.matches(input)) {
          handleShowBalance(input);
        } else if(DashboardCommands.SETTLE_UP.matches(input)) {
            handleSettleUp(input);
        }
        else if(DashboardCommands.GO_TO_PROFILE_MENU.matches(input)) {
            App.setCurrentMenu(Menu.ProfileMenu);
        } else if(DashboardCommands.LOGOUT.matches(input)) {
            App.setCurrentMenu(Menu.LoginMenu);
        }
        else {
            invalidCommand();
        }

    }
    private void handleCreateGroup(String input) {
        String name = DashboardCommands.CREATE_GROUP.getGroup(input, "name").trim();
        String type = DashboardCommands.CREATE_GROUP.getGroup(input, "type");

        Result result = DashboardController.createGroup(name, type);
        System.out.println(result.message());
    }
    public void handleAddUser(String input) {
        String username = DashboardCommands.ADD_USER.getGroup(input, "username");
        String email = DashboardCommands.ADD_USER.getGroup(input, "email");
        String groupName = DashboardCommands.ADD_USER.getGroup(input, "groupId").trim();
        int groupId = Integer.parseInt(groupName);

        Result result = DashboardController.addUser(username, email, groupId);
        System.out.println(result.message());
    }
    public void handleAddExpense(String input) {
        String split = DashboardCommands.ADD_EXPENSE.getGroup(input, "split");
        int groupId = Integer.parseInt(DashboardCommands.ADD_EXPENSE.getGroup(input, "groupId"));
        String totalExpense = DashboardCommands.ADD_EXPENSE.getGroup(input, "totalExpense");
        int numberOfUsers = Integer.parseInt(DashboardCommands.ADD_EXPENSE.getGroup(input, "numberOfUsers"));
        ArrayList<Map<String, String>> users_expenses = new ArrayList<>();
        for(int i = 0; i < numberOfUsers; i++) {
            String username = DashboardCommands.USER_INPUT_EXPENSE.getGroup(input, "username");
            String expense = "0";
            if(DashboardCommands.UNEQUAL.matches(split)) {
                expense = DashboardCommands.USER_INPUT_EXPENSE.getGroup(input, "expense");
            }
            Map<String, String> user_expense = Map.of(username, expense);
            users_expenses.add(user_expense);
        }

        Result result = DashboardController.addExpense(split, groupId, totalExpense, users_expenses);
        System.out.println(result.message());
    }
    public void handleShowBalance(String input) {
        String username = DashboardCommands.SHOW_BALANCE.getGroup(input, "username");
        Result result = DashboardController.getShowBalance(username);

        System.out.println(result.message());
    }
    public void handleSettleUp(String input) {
        String username = DashboardCommands.SETTLE_UP.getGroup(input, "username");
        Result result = DashboardController.settleUp(username);

        System.out.println(result.message());
    }
}
