package mobi.mixiong.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import mobi.mixiong.exception.HttpException;
import mobi.mixiong.util.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 处理post请求
 */
@Slf4j
public final class PostRequest extends Request {

    private HttpPost post = null;

    private HttpClient client = null;

    private HttpEntity httpEntity = null;

    private final List<NameValuePair> params = new ArrayList<NameValuePair>();

    private static final String DEFAULTCHARSET = "UTF-8";

    private String charset = DEFAULTCHARSET;

    private boolean cacheAble = true;

    public PostRequest(HttpClient client, String url) {
        this.client = client;
        post = new HttpPost(url);
    }

    public PostRequest(HttpClient httpClient, String url, String charset) {
        this(httpClient, url);
        this.charset = charset;
    }

    public PostRequest addHeader(String name, Object value) {
        if (value == null) {
            return this;
        }
        post.addHeader(name, String.valueOf(value));
        return this;
    }

    public PostRequest addHeaders(Map<String, Object> headers) {
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

    public PostRequest addParam(String name, Object value) {
        if (value == null) {
            return this;
        }
        params.add(new BasicNameValuePair(name, String.valueOf(value)));
        return this;
    }

    public PostRequest addParams(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return this;
        }
        Set<String> keys = map.keySet();
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String paramName = it.next();
            addParam(paramName, map.get(paramName));
        }
        return this;
    }

    /**
     * 设置body后，通过addParam方法添加的参数将不会设置到请求中
     *
     * @param body
     * @return
     */
    public PostRequest setBody(String body) {
        try {
            if (body == null) {
                return this;
            }
            httpEntity = new StringEntity(body, charset);
        } catch (Exception e) {
            log.error("set String body error :" + e.getMessage());
        }
        return this;
    }

    public PostRequest setJSONBody(String body) {
        try {
            if (body == null) {
                return this;
            }
            httpEntity = new StringEntity(body, ContentType.APPLICATION_JSON);
        } catch (Exception e) {
            log.error("set String body error :" + e.getMessage());
        }
        return this;
    }

    /**
     * 添加文件参数,一个POST请求添加一个文件参数
     *
     * @param name 上传文件的参数名
     * @param file
     * @return
     */
    public Request addFile(String name, File file) {
        try {
            addFile(name, file, ContentType.DEFAULT_BINARY);
        } catch (Exception e) {
            log.error("set File body error :" + e.getMessage());
        }
        return this;
    }

    /**
     * 添加文件参数,一个POST请求添加一个文件参数
     *
     * @param name        上传文件的参数名
     * @param file
     * @param contentType 默认为 application/octet-stream
     * @return
     */
    public Request addFile(String name, File file, ContentType contentType) {
        cacheAble = false;
        try {
            if (contentType == null) {
                contentType = ContentType.DEFAULT_BINARY;
            }
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addBinaryBody(name, file, contentType, file != null ? file.getName() : null);
            builder.setCharset(Charset.forName(charset));
            if (params != null && !params.isEmpty()) {
                for (NameValuePair nv : params) {
                    builder.addTextBody(nv.getName(), nv.getValue());
                }
            }
            httpEntity = builder.build();
        } catch (Exception e) {
            log.error("set File error :" + e.getMessage());
        }
        return this;
    }

    public Request addStream(String name, InputStream inputStream) {
        return addStream(name, inputStream, null);
    }

    public Request addStream(String name, InputStream inputStream, ContentType contentType) {
        cacheAble = false;
        try {
            if (contentType == null) {
                contentType = ContentType.DEFAULT_BINARY;
            }
            byte[] data = null;
            try {
                data = StreamUtils.copyToByteArray(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addBinaryBody(name, data, contentType, "tmp.jpg");
//            builder.setCharset(Charset.forName(charset));
            if (params != null && !params.isEmpty()) {
                for (NameValuePair nv : params) {
                    builder.addTextBody(nv.getName(), nv.getValue());
                }
            }
            httpEntity = builder.build();
        } catch (Exception e) {
            log.error("set File error :" + e.getMessage());
        }
        return this;
    }

    public Request addJSONBody(String name, InputStream inputStream, ContentType contentType) {
        cacheAble = false;
        try {
            if (contentType == null) {
                contentType = ContentType.DEFAULT_BINARY;
            }
            byte[] data = null;
            try {
                data = StreamUtils.copyToByteArray(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addBinaryBody(name, data, contentType, "tmp.jpg");
//            builder.setCharset(Charset.forName(charset));
            if (params != null && !params.isEmpty()) {
                for (NameValuePair nv : params) {
                    builder.addTextBody(nv.getName(), nv.getValue());
                }
            }
            httpEntity = builder.build();
        } catch (Exception e) {
            log.error("set File error :" + e.getMessage());
        }
        return this;
    }

    public String execute() throws HttpException {
        return exc(String.class);
    }



    @Override
    public byte[] download() throws HttpException {

        int status = 0;
        byte[] result = null;
        try {
            if (httpEntity == null) {
                httpEntity = new UrlEncodedFormEntity(params, charset);
            }
            post.setEntity(httpEntity);
            post.setConfig(getRequestConfig());
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            status = response.getStatusLine().getStatusCode();
            if (status >= 500) {
                log.error("request error : http status code is " + status);
                throw new HttpException("http status code not expected.");
            }
            if (entity != null) {
                try {
                    result = EntityUtils.toByteArray(entity);
                } finally {
                    EntityUtils.consume(entity);
                }
            }
        } catch (Exception e) {
            log.error("request error :" + e.getMessage());
            throw new HttpException(e);
        }
        return result;
    }

    @Override
    public JSONObject executeToJson() throws HttpException {
        return exc(JSONObject.class);
    }

    public JSONObject executeToJson(ContentType contentType) throws HttpException {
        return exc(JSONObject.class,contentType);
    }

    public <T> T executeToObject(Class<T> clazz) throws HttpException {
        return exc(clazz);
    }

    public <T> T executeToObject(TypeReference<T> clazz) throws HttpException {
        String jsonStr =  exc(String.class);
        return JSON.parseObject(jsonStr,clazz);
    }

    @Override
    public Header[] getHeaders() {
        return post.getAllHeaders();
    }

    @Override
    public String getEntity() {
        try {
            HttpEntity entity = null;
            if (httpEntity != null) {
                entity = httpEntity;
            } else if (params != null && !params.isEmpty()) {
                entity = new UrlEncodedFormEntity(params, charset);
            }
            return IOUtils.readStreamAsString(entity.getContent(), this.charset);
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public URI getUrl() {
        return post.getURI();
    }

    @Override
    public boolean cacheAble() {
        return cacheAble;
    }

    @SuppressWarnings("unchecked")
    private <T> T exc(Class<T> clazz) throws HttpException {
        T obj = null;
        int status = 0;
        String result = null;
        long startTime = System.currentTimeMillis();
        try {
            if (httpEntity == null) {
                httpEntity = new UrlEncodedFormEntity(params, charset);
            }
            post.setEntity(httpEntity);
            post.setConfig(getRequestConfig());
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            status = response.getStatusLine().getStatusCode();
            if (status >= 500) {
                log.error("request error : http status code is " + status);
                throw new HttpException("http status code not expected.");
            }
            if (entity != null) {
                try {
                    result = EntityUtils.toString(entity, charset);
                    if (clazz == null || clazz == String.class && status == 200) {
                        if (StringUtils.isNotBlank(result)) {
                            obj = (T) result;
                        }
                    } else {
                        if (clazz == JSONObject.class && status == 200) {
                            if (StringUtils.isNotBlank(result)) {
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
            log.error("request error :" + e.getMessage());
            throw new HttpException(e);
        } finally {
            long endTime = System.currentTimeMillis();
            String entityStr = null;
            try {
                entityStr = EntityUtils.toString(post.getEntity());
            } catch (IOException e) {
                log.error("IOException : " + e.getMessage());
            }
            if (StringUtils.defaultString(result, "").length() <= 512
                    || StringUtils.defaultString(entityStr, "").length() <= 512) {
                log.info("executeTime={}ms,status={},url={},entity=\n{},result=\n{}", endTime - startTime, status,
                        post.getURI(), entityStr, result);
            } else {
                log.info("executeTime={}ms,status={},url={},entity=\n" + "{},result=\n{}", endTime - startTime,
                        status, post.getURI(), entityStr.substring(0, 512), result.substring(0, 512));
            }
        }
        return obj;
    }


    private <T> T exc(Class<T> clazz,ContentType contentType) throws HttpException {
        T obj = null;
        int status = 0;
        String result = null;
        long startTime = System.currentTimeMillis();
        try {
            if (httpEntity == null) {
                httpEntity = new UrlEncodedFormEntity(params, charset);
                if(contentType!=null) {
                    ((UrlEncodedFormEntity) httpEntity).setContentType(contentType.getMimeType());
                }
            }
            post.setEntity(httpEntity);
            post.setConfig(getRequestConfig());
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            status = response.getStatusLine().getStatusCode();
            if (status >= 500) {
                log.error("request error : http status code is " + status);
                throw new HttpException("http status code not expected.");
            }
            if (entity != null) {
                try {
                    result = EntityUtils.toString(entity, charset);
                    if (clazz == null || clazz == String.class && status == 200) {
                        if (StringUtils.isNotBlank(result)) {
                            obj = (T) result;
                        }
                    } else {
                        if (clazz == JSONObject.class && status == 200) {
                            if (StringUtils.isNotBlank(result)) {
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
            log.error("request error :" + e.getMessage());
            throw new HttpException(e);
        } finally {
            long endTime = System.currentTimeMillis();
            String entityStr = null;
            try {
                entityStr = EntityUtils.toString(post.getEntity());
            } catch (IOException e) {
                log.error("IOException : " + e.getMessage());
            }
            if (StringUtils.defaultString(result, "").length() <= 512
                    || StringUtils.defaultString(entityStr, "").length() <= 512) {
                log.info("executeTime={}ms,status={},url={},entity=\n{},result=\n{}", endTime - startTime, status,
                        post.getURI(), entityStr, result);
            } else {
                log.info("executeTime={}ms,status={},url={},entity=\n" + "{},result=\n{}", endTime - startTime,
                        status, post.getURI(), entityStr.substring(0, 512), result.substring(0, 512));
            }
        }
        return obj;
    }

    public PostRequest setReadTimeOut(int readTimeOut) {
        if (readTimeOut > 0) {
            this.readTimeOut = readTimeOut;
        }
        return this;
    }

    public PostRequest setConnectTimeOut(int connectTimeOut) {
        if (connectTimeOut > 0) {
            this.connectTimeOut = connectTimeOut;
        }
        return this;
    }

    public PostRequest setWaitTimeOut(int waitTimeOut) {
        if (waitTimeOut > 0) {
            this.waitTimeOut = waitTimeOut;
        }
        return this;
    }

}
