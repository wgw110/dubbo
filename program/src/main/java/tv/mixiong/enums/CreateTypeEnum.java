package tv.mixiong.enums;

public enum CreateTypeEnum {
    SYSTEM(1, "系统创建"), USER(2, "用户创建");

    private int type;

    private String desc;

    CreateTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int type() {
        return type;
    }
}
