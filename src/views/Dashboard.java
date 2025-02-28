package views;

import controllers.DashboardController;
import models.App;
import models.Result;
import models.enums.DashboardCommands;
import models.enums.Menu;

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
            System.out.println(result.getMessage());
        } else if(DashboardCommands.ADD_USER.matches(input)) {
            handleAddUser(input);
        } else if(DashboardCommands.ADD_EXPENSE.matches(input)) {
            handleAddExpense(input);
        } else if(DashboardCommands.GO_TO_PROFILE_MENU.matches(input)) {
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
        System.out.println(result.getMessage());
    }
    public void handleAddUser(String input) {
        String username = DashboardCommands.ADD_USER.getGroup(input, "username");
        String email = DashboardCommands.ADD_USER.getGroup(input, "email");
        String groupName = DashboardCommands.ADD_USER.getGroup(input, "groupName").trim();

        Result result = DashboardController.addUser(username, email, groupName);
        System.out.println(result.getMessage());
    }
    public void handleAddExpense(String input) {
        //TODO: Implement this method
    }
}
