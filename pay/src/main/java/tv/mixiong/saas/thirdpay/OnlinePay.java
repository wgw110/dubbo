package tv.mixiong.saas.thirdpay;

import com.alibaba.fastjson.JSONObject;
import org.joda.time.DateTime;
import tv.mixiong.saas.thirdpay.config.PayConfigInfo;
import tv.mixiong.saas.thirdpay.params.RefundResponse;
import tv.mixiong.saas.transaction.entity.Transaction;

import java.util.*;

public abstract class OnlinePay<T extends PayConfigInfo> {
    protected OnlinePay(T payConfigInfo) {
        this.payConfigInfo = payConfigInfo;
    }

    protected T payConfigInfo;

    public abstract JSONObject prepay(Integer type , String name, String description, String transactionSn, long totalAmount, String userId, String realIp, long startTime, long expireTime, String redirectUrl);

    public abstract boolean checkSign(Map<String, String> callbackInputs);

    public abstract Optional<String> checkPayed(Transaction transaction, String addition);

    public abstract Map<String, String> query(String sn);

    public abstract RefundResponse refund(String transactionSn, String thirdSn, String refundSn, long totalAmount, long refundAmount, String reason);

    public abstract DateTime formatDate(String date);

    public abstract List<RefundResult> refundQuery(String sourceOrderSn);

    public abstract Optional<String> transfer(String transferSn, String openId, String realName, int amount);

    public abstract Optional<String> qrpay(String commodityName, String attach, String transactionSn,
                                  int totalMoney, String realIp, Date startTime, Date expireTime);
}
