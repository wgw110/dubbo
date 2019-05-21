package tv.mixiong.saas.thirdpay;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.SneakyThrows;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import tv.mixiong.saas.thirdpay.utils.WechatXMLConvertor;

import java.util.List;
import java.util.Map;

@Data
public class RefundResult {

    //退款订单号
    private String outRefundNo;
    //支付订单号
    private String outTradeNo;
    //金额
    private Integer refundFee;
    //交易号
    private String refundId;

    private DateTime refundSuccessTime;

    private String mchId;


    public static List<RefundResult> buildFromWechat(String result) {
        Map<String, String> inputMap = WechatXMLConvertor.XML2Map(result);
        String outTradeNo = inputMap.get("out_trade_no");
        String mchId = "wechat_" + inputMap.get("mch_id");
        List<RefundResult> list = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            String refundNoKey = "out_refund_no_";
            String refundFeeKey = "refund_fee_";
            String refundIdKey = "refund_id_";
            String refundSuccessTimeKey = "refund_success_time_";
            if (inputMap.containsKey(refundNoKey + i)) {
                String outRefundNo = inputMap.get(refundNoKey + i);
                Integer refundFee = Integer.parseInt(inputMap.get(refundFeeKey + i).toString());
                String refundId = inputMap.get(refundIdKey + i);
                DateTime refundTime = formatWechatRefundTime(inputMap.get(refundSuccessTimeKey + i));

                RefundResult refundResult = new RefundResult();
                refundResult.setOutRefundNo(outRefundNo);
                refundResult.setRefundFee(refundFee);
                refundResult.setRefundId(refundId);
                refundResult.setOutTradeNo(outTradeNo);
                refundResult.setRefundSuccessTime(refundTime);
                refundResult.setMchId(mchId);

                list.add(refundResult);
            } else {
                break;
            }
        }
        return list;
    }

    @SneakyThrows
    private static DateTime formatWechatRefundTime(String dateTime) {
        DateTimeFormatter dt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return dt.parseDateTime(dateTime);
    }
}
