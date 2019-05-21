package tv.mixiong.ds.service.im.co;

import mobi.mixiong.cache.BaseCo;
import tv.mixiong.ds.dao.entity.ImUserSign;
import org.springframework.beans.BeanUtils;

/**
 * ImUserSignCo
 * Created by scooler on 16/8/5.
 */
public class ImUserSignCo extends BaseCo {

    private Long id;

    private String userName;

    private String userSign;

    private String sdkAppId;

    private Integer expireInS; // 超时时间,单位秒

    private Integer signAt; // 签名时间戳,单位秒

    private Integer expireTime;// 超时时间戳,单位秒

    private Integer status;// 状态

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public Integer getExpireInS() {
        return expireInS;
    }

    public void setExpireInS(Integer expireInS) {
        this.expireInS = expireInS;
    }

    public Integer getSignAt() {
        return signAt;
    }

    public void setSignAt(Integer signAt) {
        this.signAt = signAt;
    }

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSdkAppId() {
        return sdkAppId;
    }

    public void setSdkAppId(String sdkAppId) {
        this.sdkAppId = sdkAppId;
    }

    public static ImUserSignCo from(ImUserSign imUserSign) {
        if (imUserSign == null) {
            return null;
        }
        ImUserSignCo co = new ImUserSignCo();
        BeanUtils.copyProperties(imUserSign, co);
        return co;
    }
}
