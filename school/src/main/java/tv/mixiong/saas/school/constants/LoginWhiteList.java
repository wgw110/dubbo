package tv.mixiong.saas.school.constants;

import java.util.HashSet;
import java.util.Set;

public class LoginWhiteList {

    private static Set<String> whiteMobile = new HashSet<String>();

    static {
        whiteMobile.add("15910993585");
        whiteMobile.add("18515591024");
    }

    public static Set<String> getWhiteMobile() {
        return whiteMobile;
    }
}
