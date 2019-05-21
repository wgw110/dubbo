package tv.mixiong.saas.thirdpay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WechatCallbackResp {

    String return_code;
    String return_msg;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
