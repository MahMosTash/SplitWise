package views;

import controllers.LoginMenuController;
import models.App;
import models.Result;
import models.enums.LoginMenuCommands;
import models.enums.Menu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine().trim();
        if (LoginMenuCommands.LOGIN.matches(input)) {
            handleLogin(input);
        } else if (LoginMenuCommands.FORGET_PASSWORD.matches(input)) {
            handleForgetPassword(input);
        } else if (LoginMenuCommands.GO_TO_SIGN_UP_MENU.matches(input)) {
            App.setCurrentMenu(Menu.SignUpMenu);
        } else if (LoginMenuCommands.Exit.matches(input)) {
            App.setCurrentMenu(Menu.Exit);
        } else {
            invalidCommand();
        }
    }
    private void handleLogin(String input) {
        String username = LoginMenuCommands.LOGIN.getGroup(input, "username");
        String password = LoginMenuCommands.LOGIN.getGroup(input, "password");

        Result loginResult = LoginMenuController.login(username, password);
        System.out.println(loginResult.getMessage());
        if(loginResult.isSuccess()) {
            App.setCurrentMenu(Menu.Dashboard);
        }
    }
    private void handleForgetPassword(String input) {
        String username = LoginMenuCommands.FORGET_PASSWORD.getGroup(input, "username");
        String email = LoginMenuCommands.FORGET_PASSWORD.getGroup(input, "email");

        Result forgetPasswordResult = LoginMenuController.forgetPassword(username, email);
        System.out.println(forgetPasswordResult.getMessage());
    }
}
