package mobi.mixiong.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;


public class CharUtil {

    private static Logger logger = LoggerFactory.getLogger(CharUtil.class);

    public static boolean isValidStringForMiXiong(String string){
        return StringUtils.isBlank(string)
                ||string.chars().allMatch(c->isChineseByScript((char)c)
                ||isEnglishByScript((char)c)
                ||isChinesePunctuation((char)c));
    }

    public static boolean  isChineseByScript(char c) {
        Character.UnicodeScript sc = Character.UnicodeScript.of(c);
        return sc == Character.UnicodeScript.HAN;
    }
    public static boolean  isEnglishByScript(char c) {
        Character.UnicodeScript sc = Character.UnicodeScript.of(c);
        return sc == Character.UnicodeScript.COMMON || sc == Character.UnicodeScript.LATIN;
    }
    public static boolean isChinesePunctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                || ub == Character.UnicodeBlock.VERTICAL_FORMS;
    }


    public static boolean isChinese(char c) {
        boolean result = false;
        if (c >= 19968 && c <= 40869) {
            result = true;
        }
        return result;
    }

    public static boolean isAllowChar(char c) {
        return c == '.';
    }


    public static boolean isEnglish(char c) {
        boolean result = false;
        if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
            result = true;
        }
        return result;
    }

    public static int getUnicodeStrLength(String str) {
        if (StringUtils.isEmpty(str)) {
            return 0;
        }
        try {
            String unicode = new String(str.getBytes("UTF-8"), "ISO-8859-1");
            return unicode.length();
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        }
        return 0;
    }

    public static String substringUnicodeStrLength(String str, int length) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        int count = 0;
        StringBuilder sb = new StringBuilder();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            String s = str.substring(i, i + 1);
            try {
                String unicode = new String(s.getBytes("UTF-8"), "ISO-8859-1");
                count += unicode.length();
                if (count < length) {
                    s = new String(unicode.getBytes("ISO-8859-1"), "UTF-8");
                    sb.append(s);
                }
            } catch (UnsupportedEncodingException e) {
                logger.error("", e);
            }
        }
        return sb.toString();
    }


    public static String getNumber(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        char[] chars = str.toCharArray();
        StringBuilder number = new StringBuilder();
        for (char ch : chars) {
            if (Character.isDigit(ch)) {
                number.append(ch);
            }
        }
        return number.toString();
    }


    public static String getSerialEnglish(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        char[] chars = str.toCharArray();
        StringBuilder english = new StringBuilder();
        boolean hasEnglish = false;
        for (char ch : chars) {
            if (isEnglish(ch)) {
                hasEnglish = true;
                english.append(ch);
            } else if (hasEnglish) {
                hasEnglish = false;
                english.append(",");
            }
        }
        return cleanStr(english);
    }
    public static String getValidString(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        char[] chars = str.toCharArray();
        StringBuilder valid = new StringBuilder();
        for (char ch : chars) {
            if (isChinese(ch) || Character.isLetterOrDigit(ch) || isAllowChar(ch)) {
                valid.append(ch);
            }
        }
        return valid.toString();
    }

    private static String cleanStr(StringBuilder sb) {
        if (sb == null) {
            return null;
        }
        if (sb.length() == 0) {
            return sb.toString();
        }
        if (sb.charAt(sb.length() - 1) == ',') {
            return sb.substring(0, sb.length() - 1);
        }
        return sb.toString();
    }

}