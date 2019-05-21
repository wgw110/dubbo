package tv.mixixiong.im.bean.template;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tv.mixixiong.im.enums.ValueType;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataItem implements Serializable {

    @JSONField(name = "key")
    private String key;

    @JSONField(name = "value")
    private String value;

    @JSONField(name = "color")
    private String color;

    @JSONField(name = "format")
    private String format;

    @JSONField(name = "type")
    private int type;

    public static DataItem from (String key, String value, String color, String format, int type) {
        return new DataItem(key, value, color, format, type);
    }

    public static DataItem genTextItem(String key, String value) {
        return genTextItem(key, value, "#000000");
    }

    public static DataItem genTextItem(String key, String value, String color) {
        DataItem dataItem = new DataItem();
        dataItem.setKey(key);
        dataItem.setValue(value);
        dataItem.setType(ValueType.TEXT.getType());
        dataItem.setColor(color);
        return dataItem;
    }

    public static DataItem genDateItem(String key, String value, String format) {
        return genDateItem(key, value, format, "#000000");
    }

    public static DataItem genDateItem(String key, String value, String format, String color) {
        DataItem dataItem = new DataItem();
        dataItem.setKey(key);
        dataItem.setValue(value);
        dataItem.setType(ValueType.DATE.getType());
        dataItem.setColor(color);
        dataItem.setFormat(format);
        return dataItem;
    }

    public static DataItem genMoneyItem(String key, String value, String format) {
        return genMoneyItem(key, value, format, "#000000");
    }

    public static DataItem genMoneyItem(String key, String value, String format, String color) {
        DataItem dataItem = new DataItem();
        dataItem.setKey(key);
        dataItem.setValue(value);
        dataItem.setType(ValueType.MONEY.getType());
        dataItem.setColor(color);
        dataItem.setFormat(format);
        return dataItem;
    }
}
