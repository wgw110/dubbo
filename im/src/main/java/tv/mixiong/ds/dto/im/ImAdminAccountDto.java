package tv.mixiong.ds.dto.im;

import com.alibaba.fastjson.annotation.JSONField;
import tv.mixiong.ds.dao.entity.ImAdminAccount;
import org.springframework.beans.BeanUtils;

/**
 * -------------------------------------
 * Author: wuzbin
 * Time  : 2017-08-01 下午4:55
 * -------------------------------------
 */
public class ImAdminAccountDto {

    @JSONField(name = "im_admin")
    private String imAdmin;

    @JSONField(name = "sdk_app_id")
    private String sdkAppId;

    @JSONField(name = "product_id")
    private Integer productId;

    public static ImAdminAccountDto from(ImAdminAccount imAdminAccount) {
        if (imAdminAccount == null) {
            return null;
        }
        ImAdminAccountDto imAdminAccountDto = new ImAdminAccountDto();
        BeanUtils.copyProperties(imAdminAccount, imAdminAccountDto);
        return imAdminAccountDto;
    }

    public String getImAdmin() {
        return imAdmin;
    }

    public void setImAdmin(String imAdmin) {
        this.imAdmin = imAdmin;
    }

    public String getSdkAppId() {
        return sdkAppId;
    }

    public void setSdkAppId(String sdkAppId) {
        this.sdkAppId = sdkAppId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
