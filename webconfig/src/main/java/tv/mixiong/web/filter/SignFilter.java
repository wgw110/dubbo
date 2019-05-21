package tv.mixiong.web.filter;

import lombok.extern.slf4j.Slf4j;
import mobi.mixiong.util.CheckSignUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tv.mixiong.web.ServerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
@Configuration
@Slf4j
public class SignFilter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (!(o instanceof HandlerMethod)) {
            return true;
        }

        if (((HandlerMethod) o).getMethodAnnotation(Sign.class) == null) {
            return true;
        }
        boolean passed = validateSign(request);
        if (!passed) {
            throw new ServerException( String.format("签名校验失败, 请求url: %s", request
                    .getRequestURI()));
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

    }

    private boolean validateSign(HttpServletRequest request) {
        String requestSign = request.getParameter("sign");//获得请求签名，如sign=19e907700db7ad91318424a97c54ed57
        if (StringUtils.isEmpty(requestSign)) {
            return false;
        }
        Map<String, String> paramsMap = getRequestParamMap(request);
        if (!paramsMap.containsKey("sign")) {
            return false;
        }
        return verifySign(paramsMap, paramsMap.get("sign"));
    }

    private String param_sign_public_key = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMGRHPht6chmKr+cyF/ABmZ1TfB6ythO" +
            "ouu2shUnz6ymtHQeKSgeKX7mT1sQrGSRwweYECmFJy7sOcDeK58DVWECAwEAAQ==";

    private Map<String, String> getRequestParamMap(HttpServletRequest request) {
        Map<String, String[]> orginMap = request.getParameterMap();
        Map<String, String> map = new HashMap<>();
        if (orginMap != null && orginMap.size() > 0) {
            orginMap.entrySet().forEach(n -> {
                if (!n.getKey().equals("t")) {
                    map.put(n.getKey(), n.getValue() != null && n.getValue().length > 0 ? n.getValue()[0] : "");
                }
            });
        }
        return map;
    }

    private boolean verifySign(Map<String, String> params, String sign) {
        try {
            return CheckSignUtil.checkSign(params, param_sign_public_key, sign);
        } catch (Exception e) {
            log.error("fail to sign", e);
            return false;
        }
    }
}
