package controllers;

import models.App;
import models.enums.LoginMenuCommands;
import models.enums.Menu;
import models.Result;
import models.User;

public class LoginMenuController {
    public static Result login(String username, String password) {
        User user = App.getUserByUsername(username);
        if(user == null) {
            return new Result(false, "username doesn't exist!");
        } else if(!user.isPasswordCorrect(password)) {
            return new Result(false, "password is incorrect!");
        }
        App.setLoggedInUser(user);
        return new Result(true, "user logged in successfully.you are now in dashboard!");
    }
    public static Result forgetPassword(String username, String email) {
        User user = App.getUserByUsername(username);
        if(user == null) {
            return new Result(false, "username doesn't exist!");
        } else if(!user.getEmail().equals(email)) {
            return new Result(false, "email doesn't match!");
        }
        return new Result(true, "password : " + user.getPassword());
    }
}
