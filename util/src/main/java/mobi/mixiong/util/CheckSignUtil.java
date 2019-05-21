package mobi.mixiong.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
public class CheckSignUtil {

    public static Boolean checkSign(Map<String, String> params, String publicKey, String signParam) throws UnsupportedEncodingException {
        if (params == null || params.isEmpty()) {
            return false;
        }
        List<String> paramNames = new ArrayList<String>();
        paramNames.addAll(params.keySet());
        Collections.sort(paramNames);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < paramNames.size(); i++) {
            String paramName = paramNames.get(i);
            if (StringUtils.isNoneBlank(params.get(paramName)) && !StringUtils.equals(paramName, "sign")) {
                sb.append(paramName).append("=").append(URLEncoder.encode(params.get(paramName),"UTF-8")).append("&");
            }
            if (StringUtils.isBlank(params.get(paramName))) {
                sb.append(paramName).append("=").append("&");
            }

        }
        String paramStr = StringUtils.removeEnd(sb.toString(), "&");
        if (signParam.indexOf("%") != -1) {
            signParam = URLDecoder.decode(signParam, "UTF-8");
        }
        log.info("paramStr:{},sign:{}", paramStr, signParam);
        boolean result = false;
        try {
            result = RSAUtils.verify(paramStr.getBytes("UTF-8"), publicKey, signParam);
        } catch (Exception e) {
            log.error("paramStr:{},sign:{}", paramStr, signParam, e);
        }
        return result;
    }

    /**
     * 签名的时候会对参数进行urldecode，如果发生异常的话，返回false
     *
     * @param paramsStr
     * @param signParam
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Boolean checkSign(String paramsStr, String signParam, String privateKey) {
        if (StringUtils.isBlank(paramsStr)) {
            return false;
        }
        String[] arr = paramsStr.split("&");
        if (arr == null || arr.length <= 0) {
            return false;
        }
        // 过滤掉sign参数和空参数
        Map<String, String> params = new HashMap<String, String>();
        for (String param : arr) {
            if (StringUtils.isNotBlank(param) && param.indexOf("=") >= 0) {
                String[] strs = param.split("=");
                if (strs.length > 1 && !strs[0].equals("sign")) {
                    params.put(strs[0], strs[1]);
                }
            }
        }
        try {
            return checkSign(params, signParam, privateKey);
        } catch (UnsupportedEncodingException e) {
            log.error("签名异常" + e);
            return false;
        }
    }
}
