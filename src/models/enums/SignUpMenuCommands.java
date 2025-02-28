package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SignUpMenuCommands implements Command {
    REGISTER("register\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s+-e\\s+(?<email>\\S+)\\s+-n\\s+(?<name>\\S+)"),
    USERNAME("[a-zA-Z][a-zA-Z0-9_\\-\\.]{3,9}"),
    PASSWORD("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{6,12}"),
    EMAIL("(?<user>\\S+)@(?<domain>\\S+)\\.(?<TLD>\\S+)"),
    NAME("[a-zA-Z]+(-[a-zA-Z]+)*"),
    DOMAIN("[A-Za-z]([a-zA-Z\\-\\.]{1,5})[A-Za-z]"),
    TLD("(org|net|edu|com)"),
    GO_TO_LOGIN_MENU("go\\s+to\\s+login\\s+menu"),
    EXIT("exit");
    private final String pattern;

    SignUpMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }
}
