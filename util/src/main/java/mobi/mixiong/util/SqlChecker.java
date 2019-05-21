package mobi.mixiong.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Stream;

public class SqlChecker {

    public static boolean isIdArray(String input) {
        if (StringUtils.isEmpty(input) || aroundWithChar(input, ',')) {
            return false;
        } else {
            String[] inputs = StringUtils.remove(input, " ").split(",");
            if (inputs.length >= 999) {
                return false;
            }
            boolean notNumeric = Arrays.asList(inputs).stream().anyMatch(one -> !StringUtils.isNumeric(one));
            return !notNumeric;
        }
    }

    public static boolean isStringArray(String input) {
        if (StringUtils.isEmpty(input) || aroundWithChar(input, ',')) {
            return false;
        } else {
            String[] inputs = input.split(",");
            if (inputs.length >= 999) {
                return false;
            }
            Stream<String> stream = Arrays.asList(inputs).stream();
            boolean isBad = stream.anyMatch(one -> aroundWithBlank(one) || StringUtils.containsAny(one, '\'', '\"', ';'));

            return !isBad;
        }
    }

    private static boolean aroundWithBlank(String input) {
        if (StringUtils.isBlank(input)) {
            return true;
        }
        if (Character.isWhitespace(input.charAt(0)) == true || Character.isWhitespace(input.charAt(input.length() - 1)) == true) {
            return true;
        }
        return false;
    }

    private static boolean aroundWithChar(String input, char ch) {
        if (StringUtils.isBlank(input)) {
            return true;
        }
        if (input.charAt(0) == ch || input.charAt(input.length() - 1) == ch) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String[] inputs = {",123", "北京", "13", "12343,234", "12343, 234", "1224,", "1233，234", "", "  ", " 哈哈", "哈'哈", "1, "};
        for (String input : inputs) {
            System.out.println(input + "--" + isStringArray(input));
        }
    }

}
