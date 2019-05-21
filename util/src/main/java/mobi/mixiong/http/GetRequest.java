package mobi.mixiong.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import mobi.mixiong.exception.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 处理GET请求
 */
@Slf4j
public final class GetRequest extends Request {

    private HttpGet get = null;

    private HttpClient client = null;

    private static final String DEFAULTCHARSET = "UTF-8";

    private String charset = DEFAULTCHARSET;

    private boolean cacheAble = true;

    public GetRequest(HttpClient client, String url) {
        this.client = client;
        get = new HttpGet(url);
    }

    public GetRequest(HttpClient httpClient, String url, String charset) {
        this(httpClient, url);
        this.charset = charset;
    }

    public GetRequest addHeader(String name, Object value) {
        if (value == null) {
            return this;
        }
        get.addHeader(name, String.valueOf(value));
        return this;
    }

    public GetRequest addHeaders(Map<String, Object> headers) {
        if (headers == null || headers.isEmpty()) {
            return this;
        }
        Set<String> keySets = headers.keySet();
        Iterator<String> it = keySets.iterator();
        while (it.hasNext()) {
            String key = it.next();
            addHeader(key, headers.get(key));
        }
        return this;
    }

    public String execute() throws HttpException {
        return exc(String.class);
    }

    public byte[] download() throws HttpException {
        cacheAble = false;
        try {
            get.setConfig(getRequestConfig());
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try {
                    byte[] result = EntityUtils.toByteArray(entity);
                    return result;
                } finally {
                    EntityUtils.consume(entity);
                }
            } else {
                throw new HttpException("empty stream");
            }
        } catch (Exception e) {
            log.error("request failure :" + e.getMessage());
            throw new HttpException(e);
        }


    }

    @Override
    public JSONObject executeToJson() throws HttpException {
        return exc(JSONObject.class);
    }

    @Override
    public <T> T executeToObject(Class<T> clazz) throws HttpException {
        return exc(clazz);
    }

    @Override
    public Header[] getHeaders() {
        return get.getAllHeaders();
    }

    @Override
    public String getEntity() {
        return "";
    }

    @Override
    public URI getUrl() {
        return get.getURI();
    }

    @Override
    public boolean cacheAble() {
        return cacheAble;
    }

    @SuppressWarnings("unchecked")
    private <T> T exc(Class<T> clazz) throws HttpException {
        T obj = null;
        String result = null;
        int status = 0;
        long startTime = System.currentTimeMillis();
        try {
            get.setConfig(getRequestConfig());
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            status = response.getStatusLine().getStatusCode();
            if(status>=500){
                log.error("request error : http status code is " + status );
                throw new HttpException("http status code not expected.");
            }
            if (entity != null) {
                try {
                    result = EntityUtils.toString(entity, charset);
                    if (clazz == null || clazz == String.class) {
                        if (StringUtils.isNotBlank(result) && status == 200) {
                            obj = (T) result;
                        }
                    } else {
                        if (clazz == JSONObject.class) {
                            if (StringUtils.isNotBlank(result) && status == 200) {
                                obj = (T) JSON.parseObject(result);
                            }
                        } else {
                            if (StringUtils.isNotBlank(result) && status == 200) {
                                obj = (T) JSON.parseObject(result, clazz);
                            }
                        }
                    }
                } finally {
                    EntityUtils.consume(entity);
                }
            }
        } catch (Exception e) {
            log.error("request failure: " + e.getMessage());
            throw new HttpException(e);
        } finally {
            long endTime = System.currentTimeMillis();
            if (StringUtils.defaultString(result, "").length() <= 1024) {
                log.info("executeTime={}ms,status={},url={},result={}", endTime - startTime, status, get.getURI(),
                        result);
            } else {
                log.info("executeTime={}ms,status={},url={},result={}", endTime - startTime, status, get.getURI(),
                        result.substring(0, 1024));
            }
        }
        return obj;
    }

    public GetRequest setReadTimeOut(int readTimeOut) {
        if (readTimeOut > 0) {
            this.readTimeOut = readTimeOut;
        }
        return this;
    }

    public GetRequest setConnectTimeOut(int connectTimeOut) {
        if (connectTimeOut > 0) {
            this.connectTimeOut = connectTimeOut;
        }
        return this;
    }

    public GetRequest setWaitTimeOut(int waitTimeOut) {
        if (waitTimeOut > 0) {
            this.waitTimeOut = waitTimeOut;
        }
        return this;
    }
}
