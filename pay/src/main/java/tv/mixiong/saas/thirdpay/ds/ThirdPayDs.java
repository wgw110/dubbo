package tv.mixiong.saas.thirdpay.ds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tv.mixiong.saas.thirdpay.cache.WechatPayConfigCache;
import tv.mixiong.saas.thirdpay.config.WechatPayConfigInfo;
import tv.mixiong.saas.thirdpay.dao.WechatPayConfigDao;
import tv.mixiong.saas.thirdpay.entity.WechatPayConfig;

@Component
public class ThirdPayDs {
    @Autowired
    @Qualifier("defaultWechatPayConfig")
    private WechatPayConfigInfo defaultWechatPayConfig;

    @Value("{pay.config.default.wechat.notifyUrl}")
    private String notifyUrl;

    @Autowired
    private WechatPayConfigDao wechatPayConfigDao;

    @Autowired
    private WechatPayConfigCache wechatPayConfigCache;

    private WechatPayConfig getWechatPayConfig(String schoolId) {
        WechatPayConfig config = wechatPayConfigCache.get(schoolId);
        if (config == null) {
            config = wechatPayConfigDao.getWechatPayConfigBySchool(schoolId);
            if (config != null) {
                wechatPayConfigCache.set(schoolId, config);
            }
        }
        return config;
    }

    /**
     * 当对应学校没有绑定商铺，此时使用默认商铺
     * @param schoolId
     * @return
     */
    public WechatPayConfigInfo getWechatPayConfigInfo(String schoolId) {
        WechatPayConfig wechatPayConfig = getWechatPayConfig(schoolId);
        if (wechatPayConfig == null) {
            return defaultWechatPayConfig;
        }
        return WechatPayConfigInfo.from(wechatPayConfig, null, notifyUrl);
    }

    public void saveOrUpdateWechatPayConfig(WechatPayConfig wechatPayConfig) {
        String schoolId = wechatPayConfig.getSchoolId();
        WechatPayConfig old = getWechatPayConfig(schoolId);
        if (old == null) {
            wechatPayConfig.setCreateTime(System.currentTimeMillis());
            wechatPayConfigDao.insertUseGeneratedKeys(wechatPayConfig);
        } else {
            wechatPayConfig.setId(old.getId());
            wechatPayConfig.setCreateTime(wechatPayConfig.getCreateTime());
            wechatPayConfig.setUpdateTime(System.currentTimeMillis());
            wechatPayConfigDao.updateByPrimaryKey(wechatPayConfig);
            wechatPayConfigCache.drop(schoolId);
        }
    }
}
