package tv.mixiong.ds.service.uc.co;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * -------------------------------------
 * Author: wuzbin
 * Time  : 2017-03-16 下午4:29
 * -------------------------------------
 */
public class UserDetailProfileVo {
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

    @JSONField(name = "description")
    private String description;

    @JSONField(name = "personal_details")
    private String personalDetails;

    @JSONField(name = "qq")
    private String qq;

    @JSONField(name = "sina")
    private String sina;

    @JSONField(name = "wx")
    private String wx;

    @JSONField(name = "wx_mp")
    private String wxMp;

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

    public int getProvider() {
        return provider;
    }

    public void setProvider(int provider) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(String personalDetails) {
        this.personalDetails = personalDetails;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSina() {
        return sina;
    }

    public void setSina(String sina) {
        this.sina = sina;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getWxMp() {
        return wxMp;
    }

    public void setWxMp(String wxMp) {
        this.wxMp = wxMp;
    }
}
