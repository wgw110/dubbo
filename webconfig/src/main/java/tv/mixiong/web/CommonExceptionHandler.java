package tv.mixiong.web;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.regex.Pattern;

@ControllerAdvice
public class CommonExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResponse resolveException(Exception e) {
        if (e instanceof ServerException) {
            ServerException exception = (ServerException) e;
//            logger.error(accessExceptionStackTraceViaPrintWriter(e));
            return new CommonResponse(exception.getStatus(), exception.getMessage());
        } else if (e instanceof ResourceNotFoundException) {
            return new CommonResponse(404, "您查找的资源不存在");
        } else if (e instanceof IllegalArgumentException) {
            return new CommonResponse(404, e.getMessage());
        } else if (e instanceof AuthException) {
            return new CommonResponse(403, "您无权进行该操作");
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return new CommonResponse(404, "您查找的资源不存在,Method");
        } else if (e instanceof HttpMediaTypeException) {
            return new CommonResponse(404, "您查找的资源不存在,MediaType");
        } else if (e instanceof MissingServletRequestParameterException) {
            logger.error(accessExceptionStackTraceViaPrintWriter(e));
            return new CommonResponse(404, "缺少参数");
        }  else if (e instanceof ServletRequestBindingException) {
            logger.info(e.getMessage());
            return new CommonResponse(403,"参数错误");
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            return new CommonResponse(405, "您查找的资源不存在,ArgType");
        }
        logger.error(accessExceptionStackTraceViaPrintWriter(e));
        return new CommonResponse(50000, "系统暂时无法提供服务");
    }

    public static String accessExceptionStackTraceViaPrintWriter(final Throwable throwable) {
        final Writer writer = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        String line = writer.toString();
        StringBuffer stringBuffer = new StringBuffer();
        String[] lines = line.split("\n");
        for (String one : lines) {
            if (isUsefulLog(one)) {
                stringBuffer.append(one).append("\n");
            }
        }
        return stringBuffer.toString();
    }


    private static List<Pattern> patterns = Lists.newArrayList(
            Pattern.compile("^\tat .*springframework.*"),
            Pattern.compile("^\tat javax\\.servlet.*"),
            Pattern.compile("^\tat org\\.apache\\.tomcat.*"),
            Pattern.compile("^\tat org\\.apache\\.catalina.*"),
            Pattern.compile("^\tat org\\.apache\\.coyote.*"),
            Pattern.compile("^\tat io\\.undertow.*")
    );

    private static boolean isUsefulLog(String line) {
        for (Pattern pattern : patterns) {
            if (pattern.matcher(line).matches()) {
                return false;
            }
        }
        return true;
    }

}
