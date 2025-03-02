package views;


import controllers.SignUpMenuController;
import models.App;
import models.Result;
import models.enums.SignUpMenuCommands;
import models.enums.Menu;
import java.util.Scanner;

public class SignUpMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine().trim();
        if (SignUpMenuCommands.REGISTER.matches(input)) {
            handleRegister(input);
        } else if (SignUpMenuCommands.GO_TO_LOGIN_MENU.matches(input)) {
            App.setCurrentMenu(Menu.LoginMenu);
        } else if (SignUpMenuCommands.EXIT.matches(input)) {
            App.setCurrentMenu(Menu.Exit);
        } else {
            invalidCommand();
        }
    }
    private void handleRegister(String input) {
        String username = SignUpMenuCommands.REGISTER.getGroup(input, "username");
        String password = SignUpMenuCommands.REGISTER.getGroup(input, "password");
        String email = SignUpMenuCommands.REGISTER.getGroup(input, "email");
        String name = SignUpMenuCommands.REGISTER.getGroup(input, "name");
        Result result = SignUpMenuController.register(username, password, email, name);
        System.out.println(result.message());
        if(result.success()) {
            App.setCurrentMenu(Menu.LoginMenu);
        }
    }
}
