package tv.mixiong.ds.service.im;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import tv.mixiong.ds.cache.im.ImUserSignCache;
import tv.mixiong.ds.dao.entity.ImUserSign;
import tv.mixiong.ds.dao.im.ImUserSignDao;
import tv.mixiong.ds.dto.im.ImAdminAccountDto;
import tv.mixiong.ds.service.im.co.ImUserSignCo;
import tv.mixiong.ds.service.uc.UserCenterDs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * im user sign
 * Created by scooler on 16/8/5.
 */
@Component
@Slf4j
public class ImUserSignDs {

    private Logger logger = LoggerFactory.getLogger(ImUserSign.class);
    @Autowired
    private ImUserSignCache imUserSignCache;

    @Autowired
    private ImUserSignDao imUserSignDao;


    @Autowired
    private ImAdminAccountDs imAdminAccountDs;

    private static final Integer OUT_TIME = 172800; // 2天的秒数, 提前2天申请新的

    @Autowired
    private UserCenterDs userCenterDs;

    public ImUserSignCo createImUserSign(int productId) {
        ImAdminAccountDto imAdminAccount = imAdminAccountDs.getImAdminAccount(productId);
        ImUserSignCo co = imUserSignCache.get(buildImUseSignKey(productId, imAdminAccount.getImAdmin()));
        if (co == null || co.isNullValue()) {
            ImUserSign imUserSign = imUserSignDao.findBySdkAppIdAndUserNameAndStatus(imAdminAccount.getSdkAppId(), imAdminAccount.getImAdmin(),1);
            if (imUserSign != null) {// 库里查到了
                // 当前秒数
                int now = (int) (System.currentTimeMillis() / 1000);
                if (imUserSign.getExpireTime() - now < OUT_TIME) {
                    // 快过期了,申请一个新的
                    imUserSign = updateUserSign(imAdminAccount);
                }
            } else {
                // 新申请一个use sign
                logger.info("[user sign log]" + JSON.toJSONString(imAdminAccount));
                imUserSign = updateUserSign(imAdminAccount);
            }
            co = ImUserSignCo.from(imUserSign);
            imUserSignCache.set(buildImUseSignKey(productId, imAdminAccount.getImAdmin()), co);
            return co;
        }
        return co.isNullValue() ? null : co;
    }


    /**
     * 刷新user sign
     */
    private ImUserSign updateUserSign(ImAdminAccountDto imAdminAccount) {
        // 让老的user sign 无效
        imUserSignDao.invalidUserSign(imAdminAccount.getSdkAppId(), imAdminAccount.getImAdmin());
        imUserSignCache.drop(buildImUseSignKey(imAdminAccount.getProductId(), imAdminAccount.getImAdmin()));
        ImUserSign userSign = userCenterDs.getAdminUserSign(imAdminAccount.getImAdmin());
        if (userSign != null) {
            userSign.setExpireTime(userSign.getExpireInS() + userSign.getSignAt());
            userSign.setStatus(1);
            userSign.setSdkAppId(imAdminAccount.getSdkAppId());
            userSign.setUserName(imAdminAccount.getImAdmin());
            imUserSignDao.insertUseGeneratedKeys(userSign);
            imUserSignCache.set(buildImUseSignKey(imAdminAccount.getProductId(), imAdminAccount.getImAdmin()), ImUserSignCo.from(userSign));
        } else {
            logger.error("无法获取user sign, passport {}, product {}", imAdminAccount.getImAdmin(), imAdminAccount.getProductId());
        }
        return userSign;
    }

    private String buildImUseSignKey(int productId, String imAdmin) {
        return String.format("%s:%s", productId, imAdmin);
    }
}