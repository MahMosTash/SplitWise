package controllers;

import models.enums.Menu;
import models.enums.Pokemon;
import models.*;

import java.util.ArrayList;
import java.util.Comparator;

public class ProfileMenuController {
    public Result showCoins() {
        User loggedInUser = Game.getLoggedInUser();
        return new Result(true, "number of coins:" + loggedInUser.getCoin());
    }

    public Result showExperience() {
        User loggedInUser = Game.getLoggedInUser();
        return new Result(true, "experience:" + loggedInUser.getExperience());
    }

    public Result showStorage() {
        User loggedInUser = Game.getLoggedInUser();
        if (loggedInUser.getStorage().isEmpty()) {
            return new Result(false, "");
        }
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 1;
        for (Card card : loggedInUser.getStorage()) {
            stringBuilder.append(counter++).append(".").append(card.getType().toLowerCase()).append(" ").append(card.getName().toLowerCase()).append(" ").append(card.getPurchaseValue()).append("\n");
        }
        if (stringBuilder.length() != 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return new Result(true, stringBuilder.toString());
    }

    public Result showDeck() {
        User loggedInUser = Game.getLoggedInUser();
        StringBuilder stringBuilder = new StringBuilder();
        if (loggedInUser.getDeck().isEmpty()) {
            return new Result(false, "");
        }
        int counter = 1;
        for (Card card : loggedInUser.getDeck()) {
            stringBuilder.append(counter++).append(".").append(card.getName()).append("\n");
        }
        if (stringBuilder.length() != 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return new Result(true, stringBuilder.toString());
    }

    public Result equipCard(String name) {
        User loggedInUser = Game.getLoggedInUser();
        if (!Card.isCardValid(name)) {
            return new Result(false, "card name is invalid");
        }
        if (!loggedInUser.haveCardFree(name)) {
            return new Result(false, "you don't have this type of card");
        }
        if (loggedInUser.isDeckFull()) {
            return new Result(false, "your deck is already full");
        }
        if (loggedInUser.pokemonAlreadyInDeck(name)) {
            return new Result(false, "you have already added this type of pokemon to your deck");
        }
        loggedInUser.equipCard(name);
        return new Result(true, "card " + name + " equipped to your deck successfully");
    }

    public Result unEquipCard(String name) {
        User loggedInUser = Game.getLoggedInUser();
        if (!Card.isCardValid(name)) {
            return new Result(false, "card name is invalid");
        }
        if (!loggedInUser.haveCardInDeck(name)) {
            return new Result(false, "you don't have this type of card in your deck");
        }
        loggedInUser.unEquipCard(name);
        return new Result(true, "card " + name + " unequipped from your deck successfully");
    }

    public Result showRank() {
        User loggedInUser = Game.getLoggedInUser();
        return new Result(true, "your rank:" + loggedInUser.getMyRank());
    }

    public Result showRanking() {
        ArrayList<User> users = Game.getUsers();
        users.sort(Comparator.comparing(User::getExperience).reversed().thenComparing(User::getUsername));
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        for (User user : users) {
            stringBuilder.append(i++).append(".").append("username:").append(user.getUsername()).append(" experience:").append(user.getExperience()).append("\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return new Result(true, stringBuilder.toString());
    }

    public void back() {
        Game.setCurrentMunu(Menu.MainMenu);
    }
}
