package tv.mixiong.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum PidEnum {
    MX(1, "米熊")

    ;

    private int type;
    private String desc;

    public int getType() {
        return type;
    }

}
