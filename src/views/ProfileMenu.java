package views;

import controllers.ProfileMenuController;
import models.App;
import models.enums.Menu;
import models.enums.ProfileMenuCommands;
import models.Result;

import java.util.Scanner;

public class ProfileMenu implements AppMenu {

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine().trim();

        if (ProfileMenuCommands.SHOW_USER_INFO.matches(input)) {
            handleShowUserInfo(input);
        } else if (ProfileMenuCommands.CHANGE_CURRENCY.matches(input)) {
            handleChangeCurrency(input);
        } else if (ProfileMenuCommands.CHANGE_USERNAME.matches(input)) {
            handleUsernameChange(input);
        } else if (ProfileMenuCommands.CHANGE_PASSWORD.matches(input)) {
            handlePasswordChange(input);
        } else if (ProfileMenuCommands.BACK.matches(input)) {
            App.setCurrentMenu(Menu.Dashboard);
            System.out.println("you are now in dashboard!");
        } else if(ProfileMenuCommands.EXIT.matches(input)) {
            App.setCurrentMenu(Menu.Exit);
        }
        else {
            invalidCommand();
        }
    }

    private void handleShowUserInfo(String input) {
        Result result = ProfileMenuController.getUserInfo();
        System.out.println(result.message());
    }
    private void handleChangeCurrency(String input) {
        String currency = ProfileMenuCommands.CHANGE_CURRENCY.getGroup(input, "currency");
        Result result = ProfileMenuController.changeCurrency(currency);
        System.out.println(result.message());
    }
    private void handleUsernameChange(String input) {
        String username = ProfileMenuCommands.CHANGE_USERNAME.getGroup(input, "username");
        Result result = ProfileMenuController.changeUsername(username);
        System.out.println(result.message());
    }
    private void handlePasswordChange(String input) {
        String oldPassword = ProfileMenuCommands.CHANGE_PASSWORD.getGroup(input, "oldPassword");
        String newPassword = ProfileMenuCommands.CHANGE_PASSWORD.getGroup(input, "newPassword");
        Result result = ProfileMenuController.changePassword(oldPassword, newPassword);
        System.out.println(result.message());
    }

}
