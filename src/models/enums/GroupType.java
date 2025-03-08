package models.enums;

public enum GroupType {
    HOME, TRIP, ZAN_O_BACHE, OTHER;

    public static GroupType getGroupType(String type) {
        switch (type) {
            case "Home":
                return HOME;
            case "Trip":
                return TRIP;
            case "Zan-o-Bache":
                return ZAN_O_BACHE;
            default:
                return OTHER;
        }
    }
    public static String getGroupType(GroupType type) {
        switch (type) {
            case HOME:
                return "Home";
            case TRIP:
                return "Trip";
            case ZAN_O_BACHE:
                return "Zan-o-Bache";
            default:
                return "Other";
        }
    }
}
