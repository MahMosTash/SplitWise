package views;

import models.enums.Menu;
import models.App;

import java.util.Scanner;

public class AppView {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        do {
            App.getCurrentMunu().checkCommand(scanner);
        } while (App.getCurrentMunu() != Menu.Exit);
    }
}
