package tv.mixixiong.im.enums;

import java.io.Serializable;

public enum TemplateType implements Serializable {

    SINGLE_PICTURE(1), MULTIPLE_PICTURE(2), NOTICE(3);

    private int type;

    TemplateType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
