package mobi.mixiong.util;

public class DecimalUtil {

    public static String getDoubleTwo(double num){
        //DecimalFormat format = new DecimalFormat("#.00");
        return String.format("%.2f", num);
    }
    public static void main(String[] args){
        System.out.println(DecimalUtil.getDoubleTwo(0.3333));
    }
}
