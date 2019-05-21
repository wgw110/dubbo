package tv.mixiong.saas.thirdpay;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import mobi.mixiong.util.IpUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tv.mixiong.saas.thirdpay.params.RefundResponse;
import tv.mixiong.saas.thirdpay.params.TradeParams;
import tv.mixiong.saas.transaction.entity.Transaction;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ThirdPayService {

    @Autowired
    ThirdPayFactory thirdPayFactory;


    public JSONObject prepay(TradeParams tradeParams) {
        if (tradeParams.getTradeType() == 0) {
            return new JSONObject();
        } else {
            OnlinePay payInstance = thirdPayFactory.get(tradeParams.getSchoolId());
            JSONObject params = payInstance.prepay(tradeParams.getTradeType(),tradeParams.getName(), tradeParams.getDescription(), tradeParams
                            .getSn

                            (), tradeParams.getPrice(), tradeParams.getOpenId(), tradeParams.getIp(), tradeParams
                            .getCreateTime()
                    , tradeParams.getExpireTime(), tradeParams.getReturnUrl());
            return params;
        }
    }

    private String getJsonValue(JSONObject param, String... keys) {
        for (String key : keys) {
            if (param.containsKey(key)) {
                return param.getString(key);
            }
        }
        return "";
    }

    public Map<String, String> query(String schoolId, String sn) {
        OnlinePay payInstance = thirdPayFactory.get(schoolId);
        return payInstance.query(sn);
    }

    public RefundResponse refund(String schoolId, String refundSn, String transactionSn, String thirdSn, long
            totalAmount, long refundAmount, String reason) {
        OnlinePay payInstance = thirdPayFactory.get(schoolId);
        return payInstance.refund(transactionSn, thirdSn, refundSn, totalAmount, refundAmount, reason);
    }

    private static DateTimeFormatter WECHAT_DATE_FORMATTER = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    private static DateTimeFormatter ALIPAY_DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    public List<RefundResult> refundQuery(String sourceOrderSn, String schoolId) {
        OnlinePay payInstance = thirdPayFactory.get(schoolId);
        return payInstance.refundQuery(sourceOrderSn);
    }

    public Optional<String> transfer(String schoolId, String transferSn, String openId, String realName, int amount) {
        OnlinePay payInstance = thirdPayFactory.get(schoolId);
        return payInstance.transfer(transferSn, openId, realName, amount);
    }

    public Optional<String> checkPayed(String schoolId, Transaction transaction, String addition) {
        OnlinePay payInstance = thirdPayFactory.get(schoolId);
        return payInstance.checkPayed(transaction,addition);
    }

    public Optional<String> wechatQrpay(String schoolId, String name, String attach, String sn, int amount, Date startTime, Date expireTime) {
        OnlinePay payInstance = thirdPayFactory.get(schoolId);
        return payInstance.qrpay(name,attach,sn,amount,IpUtils.localhost(),startTime,expireTime);
    }
}
