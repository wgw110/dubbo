package tv.mixiong.saas.thirdpay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThirdConfig {

    @Bean(name = "defaultWechatPayConfig")
    @ConfigurationProperties("pay.config.default.wechat")
    public WechatPayConfigInfo wechatPayConfigInfo(){
        return new WechatPayConfigInfo();
    }
}
