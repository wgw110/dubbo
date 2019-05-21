package tv.mixiong.saas.thirdpay.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tv.mixiong.saas.thirdpay.entity.WechatPayConfig;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WechatPayConfigInfo implements PayConfigInfo {
    private Long id ;
    private String mchId;
    private String appid;
    private String signKey;
    private String notifyUrl;
    private String sslKeyPath;


    public static WechatPayConfigInfo from (WechatPayConfig config,String signKey,String notifyUrl){
        WechatPayConfigInfo info = WechatPayConfigInfo.builder()
                .appid(config.getAppId())
                .id(config.getId())
                .mchId(config.getMchId())
                .signKey(signKey)
                .notifyUrl(notifyUrl)
                .build();
        return info;
    }
}
