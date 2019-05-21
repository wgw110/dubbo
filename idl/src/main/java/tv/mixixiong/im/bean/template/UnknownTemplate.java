package tv.mixixiong.im.bean.template;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnknownTemplate extends BaseTemplate implements Serializable {

    @JSONField(name = "type")
    private int type = 9999;

}
