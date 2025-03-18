package controllers;

import models.*;
import models.enums.ProfileMenuCommands;
import models.enums.SignUpMenuCommands;

public class ProfileMenuController {
    public static Result getUserInfo() {
        StringBuilder userInfo = new StringBuilder();
        User user = App.getLoggedInUser();
        userInfo.append("username : ").append(user.getUsername()).append("\n");
        userInfo.append("password : ").append(user.getPassword()).append("\n");
        userInfo.append("currency : ").append(user.getCurrency()).append("\n");
        userInfo.append("email : ").append(user.getEmail()).append("\n");
        userInfo.append("name : ").append(user.getName());

        return new Result(true, userInfo.toString());
    }
    public static Result changeCurrency(String currency) {
        if(!ProfileMenuCommands.CURRENCY.matches(currency)) {
            return new Result(false, "currency format is invalid!");
        }
        App.getLoggedInUser().setCurrency(currency);
        return new Result(true, "your currency changed to " + currency + " successfully!");
    }
    public static Result changeUsername(String newUsername) {
        User user = App.getLoggedInUser();
        if(user.getUsername().equals(newUsername)) {
            return new Result(false, "please enter a new username!");
        }
        if(App.getUserByUsername(newUsername) != null) {
            return new Result(false, "this username is already taken!");
        }
        if(!SignUpMenuCommands.USERNAME.matches(newUsername)) {
            return new Result(false, "new username format is invalid!");
        }
        user.setUsername(newUsername);
        return new Result(true, "your username changed to " + newUsername + " successfully!");
    }
    public static Result changePassword(String oldPassword, String newPassword) {
        User user = App.getLoggedInUser();
        if(!user.isPasswordCorrect(oldPassword)) {
            return new Result(false, "password incorrect!");
        }
        if(oldPassword.equals(newPassword)) {
            return new Result(false, "please enter a new password!");
        }
        if(!SignUpMenuCommands.PASSWORD.matches(newPassword)) {
            return new Result(false, "new password format is invalid!");
        }
        user.setPassword(newPassword);
        return new Result(true, "your password changed successfully!");
    }

}
