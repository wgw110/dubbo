package tv.mixiong.saas.account.constants;

public enum DateEnum {

    DAY(1),
    MONTH(2),
    YEAR(3);
    private int type;

    DateEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
