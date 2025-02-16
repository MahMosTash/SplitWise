package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    ShowCoins("show\\s+coins"),
    ShowExperience("show\\s+experience"),
    ShowStorage("show\\s+storage"),
    EquipCard("equip\\s+card\\s+(?<name>\\S+)\\s+to\\s+my\\s+deck"),
    UnEquipCard("unequip\\s+card\\s+(?<name>\\S+)\\s+from\\s+my\\s+deck"),
    ShowDeck("show\\s+my\\s+deck"),
    ShowRank("show\\s+my\\s+rank"),
    ShowRanking("show\\s+ranking"),
    ShowMenu("show\\s+current\\s+menu"),
    Back("back");

    private final String pattern;

    ProfileMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMather(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
