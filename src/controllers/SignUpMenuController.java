package controllers;

import models.App;
import models.Result;
import models.User;
import models.enums.SignUpMenuCommands;

public class SignUpMenuController {
    public static Result register(String username, String password, String email, String name) {
        if(!SignUpMenuCommands.USERNAME.matches(username)) {
            return new Result(false, "username format is invalid!");
        } else if(App.getUserByUsername(username) != null) {
            return new Result(false, "this username is already taken!");
        } else if(!SignUpMenuCommands.PASSWORD.matches(password)) {
            return new Result(false, "password format is invalid!");
        } else if(!validateEmail(email)) {
            return new Result(false, "email format is invalid!");

        } else if(!SignUpMenuCommands.NAME.matches(name)) {
            return new Result(false, "name format is invalid!");
        }
        App.addUser(new User(name, username, email, password));
        return new Result(true, "user registered successfully!");
    }
    private static boolean validateEmail(String email) {
        if(!SignUpMenuCommands.EMAIL.matches(email)) {
            return false;
        }
        String user = SignUpMenuCommands.EMAIL.getGroup(email, "user");
        String domain = SignUpMenuCommands.EMAIL.getGroup(email, "domain");
        String TLD = SignUpMenuCommands.EMAIL.getGroup(email, "TLD");
        if(!SignUpMenuCommands.USERNAME.matches(user)) {
            return false;
        } else if(!SignUpMenuCommands.DOMAIN.matches(domain)) {
            return false;
        } else if(!SignUpMenuCommands.TLD.matches(TLD)) {
            return false;
        }
        return true;
    }
}
