package tv.mixiong.saas.thirdpay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tv.mixiong.saas.thirdpay.config.WechatPayConfigInfo;
import tv.mixiong.saas.thirdpay.ds.ThirdPayDs;

@Slf4j
@Component
public class ThirdPayFactory {

    @Autowired
    ThirdPayDs thirdPayDs;

    public OnlinePay get(String schoolId) {
        WechatPayConfigInfo wechatPayConfigInfo = thirdPayDs.getWechatPayConfigInfo(schoolId);
        return new WechatPay(wechatPayConfigInfo);
    }
}
