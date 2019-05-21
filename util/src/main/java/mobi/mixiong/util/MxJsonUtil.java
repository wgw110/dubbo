package mobi.mixiong.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 米熊json util
 *
 */
public class MxJsonUtil {

    /**
     * 兼容ios系统 json格式不对的问题
     * @param srcJson 原始json字符串
     * @return 处理后的json
     */
    public static String formatJson(String srcJson) {
        if (StringUtils.isEmpty(srcJson)) {
            return srcJson;
        }
        return srcJson.replaceAll("\\\\n", "");
    }
    public static String removeReturn(String srcJson) {
        if (StringUtils.isEmpty(srcJson)) {
            return srcJson;
        }
        return srcJson.replaceAll("\n", "");
    }


    /**
     * 兼容ios系统 json格式不对的问题
     * @param srcJson 原始json字符串
     * @return 处理后的json
     */
    public static String formatCallbackJson(String srcJson) {
        if (StringUtils.isEmpty(srcJson)) {
            return srcJson;
        }
        return srcJson.replaceAll("\"\\{", "{").replaceAll("\\}\"", "}").replaceAll("\\\\n", "").replaceAll("\\\\\"", "\"");
    }
}
