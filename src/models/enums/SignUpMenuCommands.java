package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SignUpMenuCommands {
    Register("register\\s+username\\s+(?<username>\\S+)\\s+password\\s+(?<password>\\S+)\\s+email\\s+(?<email>\\S+@\\S+)"),
    Login("login\\s+username\\s+(?<username>\\S+)\\s+password\\s+(?<password>\\S+)"),
    Username("[a-zA-Z_]+"),
    Password("(?=.*[@#$^&!]).+"),
    PasswordStart("[a-zA-Z].+"),
    EmailDomain("\\S+@[a-z]+\\.com"),
    EmailAddress("[a-zA-Z\\d.]+@\\S+"),
    ShowMenu("show\\s+current\\s+menu"),
    Exit("exit");
    private final String pattern;

    SignUpMenuCommands(String pattern) {
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
