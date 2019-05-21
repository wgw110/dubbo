package tv.mixiong.utils.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * mx http client
 * Created by wuzbin on 16/5/26.
 */
public class MxHttpClient {

    private static CloseableHttpClient httpClient;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 2000;
    private static final String DEFAULT_CHARSET = "utf-8";
    private static final Logger LOG = LoggerFactory.getLogger(MxHttpClient.class);

    static {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", new SSLConnectionSocketFactory(SSLContextFactory.getTrustAnySSLContext()))
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultMaxPerRoute(20);
        httpClient = HttpClients.custom().setConnectionManager(connectionManager).setConnectionManagerShared(true).build();
    }

    public static <T> T get(String url, Class<T> clazz, Map<String, String> headerParams, int timeout) throws Exception {
        HttpGet httpget = new HttpGet(url);
        RequestConfig.Builder configBuilder = RequestConfig.custom().
                setConnectTimeout(timeout).
                setSocketTimeout(timeout).
                setConnectionRequestTimeout(timeout);
        requestConfig = configBuilder.build();
        httpget.setConfig(requestConfig);
        if (headerParams != null) {
            for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                httpget.setHeader(entry.getKey(), entry.getValue());
            }
        }
        ResponseHandler<String> responseHandler = new StringResponse();

        try {
            String s = httpClient.execute(httpget, responseHandler);
            return JSON.parseObject(s, clazz);
        } finally {
            httpClient.close();
        }
    }

    public static <T> T get(String url, Class<T> clazz, Map<String, String> headerParams) throws Exception {
        return get(url, clazz, headerParams, MAX_TIMEOUT);
    }

    public static <T> T get(String url, Class<T> clazz, int timeout) throws Exception {
        return get(url, clazz, null, timeout);
    }

    public static <T> T get(String url, Class<T> clazz) throws Exception {
        return get(url, clazz, null);
    }

    private static class StringResponse implements ResponseHandler<String> {
        @Override
        public String handleResponse(
                final HttpResponse response) throws IOException {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : "{}";
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }
    }

    public static String getString(String url, String charset, int timeout) {
        CloseableHttpResponse response = null;
        try {
            HttpGet get = new HttpGet(url);
            RequestConfig.Builder configBuilder = RequestConfig.custom().
                    setConnectTimeout(timeout).
                    setSocketTimeout(timeout).
                    setConnectionRequestTimeout(timeout);
            requestConfig = configBuilder.build();
            get.setConfig(requestConfig);
            response = httpClient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, charset);
        } catch (Exception e) {
            LOG.error(String.format("get请求url: %s 失败.", url), e);
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    LOG.error("关闭http client 失败", e);
                }
            }
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    LOG.error("get请求关闭 response句柄失败.", e);
                }
            }
        }
        return null;
    }

    public static String getString(String url, String charset) {
        return getString(url, charset, MAX_TIMEOUT);
    }
    /**
     * 发送 GET 请求, KV形式
     * 使用默认utf-8编码
     *
     * @param url 请求url
     * @return 返回值
     */
    public static String getString(String url) {
        return getString(url, DEFAULT_CHARSET);
    }
    public static String getString(String url, int timeout) {
        return getString(url, DEFAULT_CHARSET, timeout);
    }

    /**
     * 发送 POST 请求, KV形式
     * 使用默认utf-8编码
     *
     * @param url    请求url
     * @param params 参数map
     * @return 返回值
     */
    public static String post(String url, Map<String, String> params) {
        return post(url, params, DEFAULT_CHARSET, null);
    }

    /**
     * 发送 POST 请求, KV形式
     * 使用默认utf-8编码
     *
     * @param url    请求url
     * @param params 参数map
     * @return 返回值
     */
    public static String postWithHeader(String url, Map<String, String> params, Map<String, String> header) {
        return post(url, params, DEFAULT_CHARSET, header);
    }
    public static String post(String url, Map<String, String> params, int timeout) {
        return post(url, params, DEFAULT_CHARSET, timeout, null);
    }

    /**
     * 发送 POST 请求, KV形式
     *
     * @param url     请求url
     * @param params  参数map
     * @param charset 字符编码
     * @return 返回值
     */
    public static String post(String url, Map<String, String> params, String charset, Map<String, String> header) {
        return post(url, params, charset, MAX_TIMEOUT, header);
    }
    public static String post(String url, Map<String, String> params, String charset, int timeout, Map<String, String> header) {
        UrlEncodedFormEntity entity = null;
        if (params != null) {
            // 处理请求参数,使用charset编码
            List<NameValuePair> pairList = Lists.newArrayListWithCapacity(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue());
                pairList.add(pair);
            }
            entity = new UrlEncodedFormEntity(pairList, Charset.forName(charset));
        }
        return post(url, entity, charset, timeout, header);
    }

    /**
     * 发送 POST 请求 entity
     *
     * @param url          请求url
     * @param stringEntity entity
     * @param charset      字符编码
     * @return 返回值
     */
    private static String post(String url, StringEntity stringEntity, String charset, int timeout, Map<String, String> header) {
        String httpStr = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            if (header != null) {
                for(Map.Entry<String, String> h : header.entrySet()) {
                    httpPost.setHeader(h.getKey(), h.getValue());
                }
            }
            RequestConfig.Builder configBuilder = RequestConfig.custom().
                    setConnectTimeout(timeout).
                    setSocketTimeout(timeout).
                    setConnectionRequestTimeout(timeout);
            requestConfig = configBuilder.build();
            httpPost.setConfig(requestConfig);
            if (stringEntity != null) {
                httpPost.setEntity(stringEntity);
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, charset);
        } catch (Exception e) {
            LOG.error(String.format("post请求url: %s 失败.", url), e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    LOG.error("post请求关闭response句柄失败.", e);
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    LOG.error("关闭http client 失败", e);
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 POST 请求, KV形式
     *
     * @param url     请求url
     * @param bodyStr 参数str
     * @return 返回值
     */
    public static String postWithBody(String url, String bodyStr) {
        return postWithBody(url, bodyStr, MAX_TIMEOUT);
    }
    public static String postWithBody(String url, String bodyStr, int timeout) {
        StringEntity entity = null;
        if (!StringUtils.isEmpty(bodyStr)) {
            entity = new StringEntity(bodyStr, Charset.forName(DEFAULT_CHARSET));
        }
        return post(url, entity, DEFAULT_CHARSET, timeout, null);
    }

    public static Integer getCode(String res) {
        if (!StringUtils.isEmpty(res)) {
            JSONObject json = JSON.parseObject(res);
            if (json.containsKey("status")) {
                return json.getInteger("status");
            }
            return -1;
        }
        return -1;
    }

    public static JSONObject getJson(String res) {
        if (!StringUtils.isEmpty(res)) {
            JSONObject json = JSON.parseObject(res);
            if (json.containsKey("data")) {
                return json.getJSONObject("data");
            }
            return null;
        }
        return null;
    }

    public static JSONArray getJsonArray(String res) {
        if (!StringUtils.isEmpty(res)) {
            JSONObject json = JSON.parseObject(res);
            if (json.containsKey("data")) {
                return json.getJSONArray("data");
            }
            return null;
        }
        return null;
    }

}
