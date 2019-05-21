package tv.mixixiong.im.bean.template;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice implements Serializable {

    @JSONField(name = "first")
    private String first;

    @JSONField(name = "data")
    private List<DataItem> data;

    @JSONField(name = "remark")
    private String remark;


    public static Notice from (String first, List<DataItem> data, String remark) {
        Notice notice = new Notice();
        notice.setFirst(first);
        notice.setData(data);
        notice.setRemark(remark);
        return notice;
    }

    public static Notice from (String first, List<DataItem> data) {
        return from(first, data, "");
    }
}
