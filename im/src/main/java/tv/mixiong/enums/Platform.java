package tv.mixiong.enums;

/**
 * .
 */
public enum Platform {
    IPAD(1, "iPad"),
    IPHONE(3, "iPhone"),
    ANDROID_PAD(5, "GPad"),
    ANDROID_PHONE(6, "GPhone"),
    WINDOWS(7, "Obs for windows"),
    MAC(8, "Obs for mac"),
    H5(18, "H5");
    private int type;
    private String value;
    Platform(int type, String value){
        this.type = type;
        this.value = value;
    }

    public int getType(){
        return type;
    }

    public static boolean isAndroid(int type) {
       return type == Platform.ANDROID_PAD.type || type == Platform.ANDROID_PHONE.type;
    }

    public static boolean isIos(int type) {
        return type == Platform.IPAD.type || type == Platform.IPHONE.type;
    }

    public static boolean isOk(int type) {
        for (Platform platform : Platform.values()) {
            if (platform.type == type) {
                return true;
            }
        }
        return false;
    }
}
