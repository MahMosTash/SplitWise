package views;

import controllers.MainMenuController;
import controllers.ProfileMenuController;
import models.enums.MainMenuCommands;
import models.enums.ProfileMenuCommands;
import models.Game;
import models.Result;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu implements AppMenu {
    private final ProfileMenuController menuController = new ProfileMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((ProfileMenuCommands.ShowCoins.getMather(input)) != null) {
            System.out.println(menuController.showCoins());
        } else if (ProfileMenuCommands.ShowExperience.getMather(input) != null) {
            System.out.println(menuController.showExperience());
        } else if (ProfileMenuCommands.ShowStorage.getMather(input) != null) {
            Result result = menuController.showStorage();
            if (result.isSuccessful()) System.out.println(result);
        } else if (ProfileMenuCommands.ShowDeck.getMather(input) != null) {
            Result result = menuController.showDeck();
            if (result.isSuccessful()) System.out.println(result);
        } else if ((matcher = ProfileMenuCommands.EquipCard.getMather(input)) != null) {
            System.out.println(menuController.equipCard(matcher.group("name")));
        } else if ((matcher = ProfileMenuCommands.UnEquipCard.getMather(input)) != null) {
            System.out.println(menuController.unEquipCard(matcher.group("name")));
        } else if (ProfileMenuCommands.ShowRank.getMather(input) != null) {
            System.out.println(menuController.showRank());
        } else if (ProfileMenuCommands.ShowRanking.getMather(input) != null) {
            System.out.println(menuController.showRanking());
        } else if (ProfileMenuCommands.ShowMenu.getMather(input) != null) {
            System.out.println("profile menu");
        } else if (ProfileMenuCommands.Back.getMather(input) != null) {
            menuController.back();
        } else {
            System.out.println("invalid command");
        }
    }
}
