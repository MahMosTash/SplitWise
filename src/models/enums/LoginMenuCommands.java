package models.enums;


public enum LoginMenuCommands implements Command {
    LOGIN("login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)"),
    FORGET_PASSWORD("forget-password\\s+-u\\s+(?<username>\\S+)\\s+-e\\s+(?<email>\\S+)"),
    GO_TO_SIGN_UP_MENU("go\\s+to\\s+signup\\s+menu"),
    EXIT("exit");

    private final String pattern;

    LoginMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }
}