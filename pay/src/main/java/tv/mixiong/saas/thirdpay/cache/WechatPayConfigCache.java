package tv.mixiong.saas.thirdpay.cache;

import mobi.mixiong.cache.BaseValueCache;
import org.springframework.stereotype.Component;
import tv.mixiong.saas.thirdpay.entity.WechatPayConfig;
@Component
public class WechatPayConfigCache extends BaseValueCache<String,WechatPayConfig> {
    @Override
    protected String getPrefix() {
        return "mx:saas:pay:wechat:config:";
    }
}
