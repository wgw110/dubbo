package tv.mixixiong.im.enums;


import java.io.Serializable;

public enum ValueType implements Serializable {
    TEXT(1),
    DATE(2),
    MONEY(3),
    ;

    private int type;

    ValueType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
