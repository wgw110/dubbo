package tv.mixiong.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class UrlUtil {

    private static Logger logger = LoggerFactory.getLogger(UrlUtil.class);

    public static String encode(String content, String charset) {
        if (StringUtils.isEmpty(content)) {
            return content;
        }
        try {
            String encode = URLEncoder.encode(content, charset);
            // 兼容ios，将空格 encode 之后 变成 + 号的问题
            encode = encode.replaceAll("\\+", "%20");
            return encode;
        } catch (UnsupportedEncodingException e) {
            logger.error("encode error :" + content, e);
        }
        return content;
    }

    public static String encodeUtf8(String content) {
        return encode(content, "utf-8");
    }
}
