package models.enums;

public enum DashboardCommands implements Command {
    CREATE_GROUP("create-group\\s+-n\\s+(?<name>.+)\\s+-t\\s+(?<type>\\S+)"),
    TYPE("(Home|Trip|Zan-o-Bache|Other)"),
    NAME("[a-zA-Z0-9!@#$%^&* ]{4,30}"),
    SHOW_MY_GROUPS("show\\s+my\\s+groups"),
    ADD_USER("add-user\\s+-u\\s+(?<username>\\S+)\\s+-e\\s+(?<email>\\S+)\\s+-g\\s+(?<groupName>.+)"),
    ADD_EXPENSE("add-expense\\s+-g\\s+(?<groupName>.+)\\s+-t\\s+(?<totalExpense>\\d+)\\s+-e\\s+(?<expenses>.+)"), //TODO: Implement this command
    GO_TO_PROFILE_MENU("go\\s+to\\s+profile\\s+menu"),
    LOGOUT("logout"),
    EXIT("Exit");

    private final String command;

    DashboardCommands(String command) {
        this.command = command;
    }

    @Override
    public String getPattern() {
        return command;
    }
}
