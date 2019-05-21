package mobi.mixiong.http;

import com.alibaba.fastjson.JSONObject;
import mobi.mixiong.exception.HttpException;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;

import java.net.URI;

public abstract class Request {

    // 最大等待时间
    private static final int WAIT_TIMEOUT = 1000;
    // 链接超时时间
    private static final int CONNECT_TIMEOUT = 500;
    // 读取超时时间
    private static final int READ_TIMEOUT = 1000;

    protected int readTimeOut = READ_TIMEOUT;

    protected int connectTimeOut = CONNECT_TIMEOUT;

    protected int waitTimeOut = WAIT_TIMEOUT;


    protected RequestConfig getRequestConfig() {
        return RequestConfig.custom().setSocketTimeout(readTimeOut).setConnectTimeout(connectTimeOut).setConnectionRequestTimeout(waitTimeOut).build();
    }

    /**
     * 执行请求并返回结果
     *
     * @return
     */
    public abstract String execute() throws HttpException;

    public abstract byte[] download() throws HttpException;

    /**
     * 执行请求并返回指定类的对象，此方法在ContextType是 application/json情况下执行正确
     *
     * @return
     * @see org.apache.http.entity.ContentType APPLICATION_JSON
     */
    public abstract JSONObject executeToJson() throws HttpException;

    /**
     * 执行请求并返回指定类的对象，此方法在ContextType是 application/json情况下执行正确
     *
     * @param clazz
     * @return
     * @see org.apache.http.entity.ContentType APPLICATION_JSON
     */
    public abstract <T> T executeToObject(Class<T> clazz) throws HttpException;

    public abstract Header[] getHeaders();

    public abstract String getEntity();

    public abstract URI getUrl();

    public abstract boolean cacheAble();

}
