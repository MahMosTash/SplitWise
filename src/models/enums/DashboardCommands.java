package models.enums;

public enum DashboardCommands implements Command {
    CREATE_GROUP("create-group\\s+-n\\s+(?<name>.+)\\s+-t\\s+(?<type>\\S+)"),
    TYPE("(Home|Trip|Zan-o-Bache|Other)"),
    NAME("[a-zA-Z0-9!@#$%^&* ]{4,30}"),
    SHOW_MY_GROUPS("show\\s+my\\s+groups"),
    ADD_USER("add-user\\s+-u\\s+(?<username>\\S+)\\s+-e\\s+(?<email>\\S+)\\s+-g\\s+(?<groupId>\\d+)"),
    UNEQUAL("unequally"),
    ADD_EXPENSE("add-expense\\s+-g\\s+(?<groupId>\\d+)\\s+-s\\s+(?<split>equally|unequally)\\s+-t\\s+(?<totalExpense>\\S+)\\s+-n\\s+(?<numberOfUsers>\\d+)"),
    USER_INPUT_EXPENSE("(?<username>\\S+)(?:\\s(?<expense>\\d+))?"),
    NUMBER("\\d+"),
    SHOW_BALANCE("show\\s+balance\\s+-u\\s+(?<username>\\S+)"),
    SETTLE_UP("settle-up\\s+-u\\s+(?<username>\\S+)"),
    GO_TO_PROFILE_MENU("go\\s+to\\s+profile\\s+menu"),
    LOGOUT("logout"),
    EXIT("exit");

    private final String command;

    DashboardCommands(String command) {
        this.command = command;
    }

    @Override
    public String getPattern() {
        return command;
    }
}
