package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Command {
    String getPattern();

    default Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(getPattern()).matcher(input);

        matcher.matches();
        return matcher;
    }

    default boolean matches(String input) {
        return getMatcher(input).matches();
    }

    default String getGroup(String input, String group) {
        return getMatcher(input).group(group);
    }
}