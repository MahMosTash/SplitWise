package views;

import controllers.LoginMenuController;
import models.enums.LoginMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu {
    private final LoginMenuController menuController = new LoginMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = LoginMenuCommands.Register.getMather(input)) != null){
            System.out.println(menuController.register(matcher.group("username"), matcher.group("password"), matcher.group("email")));
        } else if ((matcher = LoginMenuCommands.Login.getMather(input)) != null) {
            System.out.println(menuController.login(matcher.group("username"), matcher.group("password")));
        } else if (LoginMenuCommands.Exit.getMather(input) != null) {
            menuController.exit();
        }else if (LoginMenuCommands.ShowMenu.getMather(input) != null) {
            System.out.println("login menu");
        }else {
            System.out.println("invalid command");
        }
    }
}
