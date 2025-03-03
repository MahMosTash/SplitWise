package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands implements Command {
    SHOW_USER_INFO("show\\s+user\\s+info"),
    CHANGE_CURRENCY("change-currency\\s+-n\\s+(?<currency>\\S+)"),
    CURRENCY("[A-Z]{3}"),
    CHANGE_USERNAME("change-username\\s+-n\\s+(?<username>\\S+)"),
    CHANGE_PASSWORD("change-password\\s+-o\\s+(?<oldPassword>\\S+)\\s+-n\\s+(?<newPassword>\\S+)"),
    Back("back"),
    EXIT("exit");

    private final String pattern;

    ProfileMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }
}
