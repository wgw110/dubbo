package tv.mixiong.web;

import com.google.common.collect.ImmutableMap;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends
            HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse response) {

        ResponseBody responseBody = methodParameter.getMethodAnnotation(ResponseBody.class);
        if (responseBody != null) {
            return o;
        }
        if (mediaType != null && mediaType.toString().indexOf("image") == -1) { //图片不处理
            if (o != null) {
                if (o instanceof CommonResponse) {
                    return o;
                } else if(o instanceof  Boolean){
                    return new CommonResponse(ImmutableMap.of("success",o));
                }else {
                    return new CommonResponse(o);
                }
            } else {
                return new CommonResponse();
            }
        }
        return o;
    }

}
