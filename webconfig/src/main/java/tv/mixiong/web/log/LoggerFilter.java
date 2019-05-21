package tv.mixiong.web.log;

import lombok.extern.slf4j.Slf4j;
import mobi.mixiong.util.IdWorker;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@Slf4j
public class LoggerFilter extends OncePerRequestFilter {
    private static final String REQUEST_PREFIX = "Request: ";
    private static final String RESPONSE_PREFIX = "Response: ";
    private static final List<String> HEAD_NOT_INCLUDE = Arrays.asList("Accept", "Accept-Encoding", "Accept-Charset",
            "Accept-Language", "Connection", "Content-Encoding", "Content-Type", "Vary", "Cache-Control", "Cookie",
            "Host", "accept", "accept-encoding", "accept-charset", "accept-language", "connection", "content-encoding",
            "content-type", "vary", "cache-control", "cookie", "host");

    private static final String METHOD_OPTIONS = "OPTIONS";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, final FilterChain
            filterChain) throws ServletException, IOException {
        try {
            String requestId = IdWorker.nextString();
            MDC.put("traceId", requestId);
            request = new RequestWrapper(requestId, request);
            response = new ResponseWrapper(requestId, response);
            filterChain.doFilter(request, response);
        } finally {
            logRequest(request);
            logResponse((ResponseWrapper) response);
        }
    }

    private void logRequest(final HttpServletRequest request) {
        StringBuilder msg = new StringBuilder();
        if (request.getMethod() != null) {
            String method = request.getMethod();
            if (!METHOD_OPTIONS.equals(method)) {
                msg.append("Method:[").append(request.getMethod()).append("], ");
            } else {
                //OPTIONS 不打日志
                return;
            }
        }
        msg.append(REQUEST_PREFIX);
        if (request instanceof RequestWrapper) {
            msg.append("ID: [").append(((RequestWrapper) request).getId()).append("], ");
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            msg.append("Session:[").append(session.getId()).append("], ");
        }
        if (request.getContentType() != null) {
            msg.append("Content-Type:[").append(request.getContentType()).append("], ");
        }
        Enumeration<String> headers = request.getHeaderNames();
        msg.append("Headers:[");
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            if (!HEAD_NOT_INCLUDE.contains(header)) {
                msg.append(header).append(":").append(request.getHeader(header)).append(",");
            }
        }
        msg.append("], ");
        msg.append("Url:[").append(request.getRequestURI());
        if (request.getQueryString() != null) {
            msg.append('?').append(request.getQueryString());
        }
        msg.append("], ");

        Enumeration<String> params = request.getParameterNames();
        msg.append("Form:[");
        while (params.hasMoreElements()) {
            String key = params.nextElement();
            msg.append(key).append(":").append(request.getParameter(key)).append(",");
        }
        msg.append("] ");

        if (request instanceof RequestWrapper && !isMultipart(request) && !isBinaryContent(request)) {
            RequestWrapper requestWrapper = (RequestWrapper) request;
            try {
                String charEncoding = requestWrapper.getCharacterEncoding() != null ? requestWrapper
                        .getCharacterEncoding() :
                        "UTF-8";
                msg.append(", Payload[").append(new String(requestWrapper.toByteArray(), charEncoding)).append("]");
            } catch (UnsupportedEncodingException e) {
                log.warn("Failed to parse request payload", e);
            }
        }
        log.info(msg.toString());
    }

    private boolean isBinaryContent(final HttpServletRequest request) {
        if (request.getContentType() == null) {
            return false;
        }
        return request.getContentType().startsWith("image") || request.getContentType().startsWith("video")
                || request.getContentType().startsWith("audio") || request.getContentType().contains("octet-stream");
    }

    private boolean isBinaryContent(final HttpServletResponse response) {
        if (response.getContentType() == null) {
            return false;
        }
        return response.getContentType().startsWith("image") || response.getContentType().startsWith("video")
                || response.getContentType().startsWith("audio") || response.getContentType().contains("octet-stream");
    }

    private boolean isMultipart(final HttpServletRequest request) {
        return request.getContentType() != null && request.getContentType().startsWith("multipart/form-data");
    }

    private void logResponse(final ResponseWrapper response) {
        StringBuilder msg = new StringBuilder();
        msg.append(RESPONSE_PREFIX);
        msg.append("ID: [").append((response.getId()));
        try {
            if (response instanceof ResponseWrapper && !isBinaryContent(response)) {
                msg.append("], Payload[").append(new String(response.toByteArray(), response.getCharacterEncoding()));
            } else {
                msg.append("], Payload[").append("binary content");
            }
        } catch (UnsupportedEncodingException e) {
            log.warn("Failed to parse response payload", e);
        } finally {
            msg.append("] ");
        }
        log.info(msg.toString());
    }
}
