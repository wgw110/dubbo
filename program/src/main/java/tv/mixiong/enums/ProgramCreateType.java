package tv.mixiong.enums;


public enum ProgramCreateType {

    USER_CREATE(1, "用户创建"), MANAGE_CREATE(2, "后台创建");

    private int type;
    private String desc;

    ProgramCreateType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

