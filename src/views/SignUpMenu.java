package views;

import controllers.ShopMenuController;
import models.enums.ShopMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SignUpMenu implements AppMenu {
    private final ShopMenuController menuController = new ShopMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = ShopMenuCommands.BuyCard.getMather(input)) != null) {
            System.out.println(menuController.buyCard(matcher.group("name")));
        } else if ((matcher = ShopMenuCommands.SellCard.getMather(input)) != null) {
            System.out.println(menuController.sellCard(matcher.group("name")));
        } else if (ShopMenuCommands.ShowMenu.getMather(input) != null) {
            System.out.println("shop menu");
        } else if (ShopMenuCommands.Back.getMather(input) != null) {
            menuController.back();
        } else {
            System.out.println("invalid command");
        }
    }
}
