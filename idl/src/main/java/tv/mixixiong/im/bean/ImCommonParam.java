package tv.mixixiong.im.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImCommonParam implements Serializable {

    @JSONField(name = "user_action")
    private String userAction;

    @JSONField(name = "account")
    private String account;

    @Override
    public String toString() {
        return "ImCommonParam{" +
                "userAction='" + userAction + '\'' +
                ", account='" + account + '\'' +
                '}';
    }

    public static ImCommonParam from (String userAction, String account) {
        ImCommonParam param = new ImCommonParam();
        param.setAccount(account);
        param.setUserAction(userAction);
        return param;
    }

    public static ImCommonParam genTemplateCommonParam(String account) {
        return from("20018", account);
    }
}
