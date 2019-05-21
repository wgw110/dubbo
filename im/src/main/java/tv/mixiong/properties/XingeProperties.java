package tv.mixiong.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "xinge")
public class XingeProperties {

    private String androidAppId;

    private String androidSecretKey;

    private String iosAppId;

    private String iosSecretKey;

    private int env;

    public int getEnv() {
        return env;
    }

    public void setEnv(int env) {
        this.env = env;
    }

    public String getAndroidAppId() {
        return androidAppId;
    }

    public void setAndroidAppId(String androidAppId) {
        this.androidAppId = androidAppId;
    }

    public String getAndroidSecretKey() {
        return androidSecretKey;
    }

    public void setAndroidSecretKey(String androidSecretKey) {
        this.androidSecretKey = androidSecretKey;
    }

    public String getIosAppId() {
        return iosAppId;
    }

    public void setIosAppId(String iosAppId) {
        this.iosAppId = iosAppId;
    }

    public String getIosSecretKey() {
        return iosSecretKey;
    }

    public void setIosSecretKey(String iosSecretKey) {
        this.iosSecretKey = iosSecretKey;
    }
}
