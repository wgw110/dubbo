package tv.mixiong.saas.pay.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 */
@Slf4j
public class SNGenerator {

    public static final String TRANSACTION_PRE = "T";
    public static final String TRANSACTION_PRE_TEST = "P";

    public static final String TRANSACTION_REFUND_PRE = "R";
    public static final String TRANSACTION_REFUND_PRE_TEST = "X";

    public static final DateTimeFormatter ORDER_DATE_FORMAT = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    public static final int TRANSACTION_RANDOM_NUMBER = 4;

    //Integer.toHexString(Integer.MAX_VALUE).length()  ==  8
    //Long.toHexString(Long.MAX_VALUE) == 16
    public static String generateOrderSnByCommodityId(int commodityId) {
        //商品ID最大为20亿,也就是10位,转化成16进制的,缩短长度,空处8位来作为商品ID,不足的在前面补随机数加一个0
        String commodityID_16 = Integer.toHexString(commodityId);
        int commodityID_length=commodityID_16.length();
        String commodityStr = commodityID_16.toUpperCase();//8位
        int randomStr_length= 8 -  commodityID_length;
        if(randomStr_length-1>0){
            commodityStr= RandomStringUtils.randomAlphabetic(randomStr_length-1).toUpperCase()+"0"+commodityStr;
        }
        //时间标示10位
        String dateTimeStr= LocalDateTime.now().format(ORDER_DATE_FORMAT);
        //时间10位和商品ID做合并,最后补两位的随机数
        String orderSN = dateTimeStr+commodityStr+RandomStringUtils.randomNumeric(2);
        return orderSN;
    }

    public static String generateTransactionSnByOrderSn(String orderSn,boolean online){
        String transactionSN = (online?TRANSACTION_PRE:TRANSACTION_PRE_TEST)+ StringUtils.reverse(orderSn)+RandomStringUtils.randomAlphabetic(TRANSACTION_RANDOM_NUMBER).toUpperCase();
        return transactionSN;
    }

    public static String generateRefundTransactionSnByOrderSn(String orderSn,boolean online){
        String transactionSN = (online?TRANSACTION_REFUND_PRE:TRANSACTION_REFUND_PRE_TEST)+ StringUtils.reverse(orderSn)+RandomStringUtils.randomAlphabetic(TRANSACTION_RANDOM_NUMBER).toUpperCase();
        return transactionSN;
    }

    public static String generateTransferSnByUserId(int userID) {
        String commodityID_16 = Integer.toHexString(userID);
        int commodityID_length=commodityID_16.length();
        String commodityStr = commodityID_16.toUpperCase();//8位
        int randomStr_length= 8 -  commodityID_length;
        if(randomStr_length-1>0){
            commodityStr= RandomStringUtils.randomAlphabetic(randomStr_length-1).toUpperCase()+"0"+commodityStr;
        }
        String dateTimeStr= LocalDateTime.now().format(ORDER_DATE_FORMAT);
        String transferSN = dateTimeStr+commodityStr+RandomStringUtils.randomNumeric(2);
        return transferSN;
    }

    public static String computeOrderSnByTransactionSn(String transactionSn) {
        String orderSn = StringUtils.substring(transactionSn, 1, transactionSn.length() - TRANSACTION_RANDOM_NUMBER);
        orderSn = StringUtils.reverse(orderSn);
        return orderSn;
    }

    public static int computeUserIdByTransferSn(String transferSn) {
        if(transferSn==null){
            return 0;
        }
        String commodityStr=transferSn.substring(12,20);
        int zero_position=commodityStr.indexOf("0");
        if(zero_position!=-1){
            commodityStr=commodityStr.substring(zero_position+1);
        }
        return Integer.parseInt(commodityStr,16);
    }

    public static int computeCommodityIdByOrderSn(String orderSn) {
        if(orderSn==null){
            return 0;
        }
        String commodityStr=orderSn.substring(12,20);
        int zero_position=commodityStr.indexOf("0");
        if(zero_position!=-1){
            commodityStr=commodityStr.substring(zero_position+1);
        }
        return Integer.parseInt(commodityStr,16);
    }

}
