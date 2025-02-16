package controllers;

import models.enums.LoginMenuCommands;
import models.enums.Menu;
import models.Game;
import models.Result;
import models.User;

public class LoginMenuController {
    public Result register(String username, String password, String email) {
        if (LoginMenuCommands.Username.getMather(username) == null) {
            return new Result(false, "username format is invalid");
        }
        if (!isUsernameUnique(username)) {
            return new Result(false, "username already exists");
        }
        if ((password.length() < 6) || (password.length() > 18)) {
            return new Result(false, "password length too small or short");
        }
        if (LoginMenuCommands.Password.getMather(password) == null) {
            return new Result(false, "your password must have at least one special character");
        }
        if (LoginMenuCommands.PasswordStart.getMather(password) == null) {
            return new Result(false, "your password must start with english letters");
        }
        if (LoginMenuCommands.EmailDomain.getMather(email) == null) {
            return new Result(false, "email format is invalid");
        }
        if (LoginMenuCommands.EmailAddress.getMather(email) == null){
            return new Result(false, "you can't use special characters");
        }
        if (email.split("\\.").length > 3) {
            return new Result(false, "you can't use special characters");
        }
        Game.addUser(new User(username, password));
        return new Result(true, "user registered successfully");
    }
    public Result login(String username, String password){
        User user;
        if ((user = getUserByUsername(username)) == null){
            return new Result(false, "username doesn't exist");
        }
        if (!user.getPassword().equals(password)){
            return new Result(false, "password is incorrect");
        }
        Game.setLoggedInUser(user);
        Game.setCurrentMunu(Menu.MainMenu);
        return new Result(true, "user logged in successfully");
    }
    public void exit(){
        Game.setCurrentMunu(Menu.Exit);
    }
    private boolean isUsernameUnique(String username) {
        return getUserByUsername(username) == null;
    }

    private User getUserByUsername(String username) {
        for (User user : Game.getUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
