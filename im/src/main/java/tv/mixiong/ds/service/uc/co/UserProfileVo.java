package tv.mixiong.ds.service.uc.co;

import com.alibaba.fastjson.annotation.JSONField;
import mobi.mixiong.cache.BaseCo;
import org.springframework.beans.BeanUtils;

/**
 * Created by wuzbin on 16/5/17.
 */
public class UserProfileVo extends BaseCo {

    @JSONField(name = "passport")
    private String passport;

    @JSONField(name = "nickname")
    private String nickname;

    @JSONField(name = "avatar")
    private String avatar;

    @JSONField(name = "signature")
    private String signature;

    @JSONField(name = "verify_type")
    private Integer  verifyType;

    @JSONField(name = "verify_info")
    private String verifyInfo;

    @JSONField(name = "provider")
    private int provider;

    @JSONField(name = "is_celebrity")
    private boolean celebrity; // 是否达人

    @JSONField(name = "is_verified")
    private boolean verified; // 是否实名认证

    public static UserProfileVo from(UserDetailProfileVo user) {
        if (user == null) {
            return null;
        }
        UserProfileVo vo = new UserProfileVo();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }

    public int getProvider() {
        return provider;
    }

    public void setProvider(int provider) {
        this.provider = provider;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(Integer verifyType) {
        this.verifyType = verifyType;
    }

    public String getVerifyInfo() {
        return verifyInfo;
    }

    public void setVerifyInfo(String verifyInfo) {
        this.verifyInfo = verifyInfo;
    }

    public void setProvider(Integer provider) {
        this.provider = provider;
    }

    public boolean isCelebrity() {
        return celebrity;
    }

    public void setCelebrity(boolean celebrity) {
        this.celebrity = celebrity;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
