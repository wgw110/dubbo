package tv.mixiong.saas.thirdpay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import mobi.mixiong.exception.HttpException;
import mobi.mixiong.http.HttpClientUtil;
import mobi.mixiong.util.IpUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import tv.mixiong.dubbo.exception.ServiceException;
import tv.mixiong.saas.thirdpay.config.WechatPayConfigInfo;
import tv.mixiong.saas.thirdpay.constants.TradeType;
import tv.mixiong.saas.thirdpay.params.RefundResponse;
import tv.mixiong.saas.thirdpay.utils.WechatXMLConvertor;
import tv.mixiong.saas.transaction.entity.Transaction;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
public class WechatPay extends OnlinePay<WechatPayConfigInfo> {
    // 统一下单地址
    private final String unifiedOrder = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    // 查询订单地址
    private final String orderQuery = "https://api.mch.weixin.qq.com/pay/orderquery";
    // 关闭订单地址
    private final String closeOrder = "https://api.mch.weixin.qq.com/pay/closeorder";
    // 申请退款地址
    private final String refund = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    // 查询退款地址
    private final String refundQuery = "https://api.mch.weixin.qq.com/pay/refundquery";
    // 企业付款
    private final String transfers = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";// 需要证书
    private final String downloadbill = "https://api.mch.weixin.qq.com/pay/downloadbill";

    protected WechatPay(WechatPayConfigInfo payConfigInfo) {
        super(payConfigInfo);
    }

    @Override
    public JSONObject prepay(Integer type , String name, String description, String transactionSn, long totalAmount, String userId,
                             String realIp, long startTime, long expireTime, String redirectUrl) throws
            ServiceException {
        String tradeType = TradeType.valueOf(type).getDesc();
        Map<String, String> object = unifiedOrder(name, description, transactionSn, totalAmount, realIp,
                getWechatTimeStr(startTime), getWechatTimeStr(expireTime), payConfigInfo.getNotifyUrl(), tradeType,
                userId);

        if (tradeType.equals("JSAPI")) {
            Map<String, String> toH5 = new HashMap<String, String>();
            toH5.put("appId", payConfigInfo.getAppid());
            toH5.put("timeStamp", "" + System.currentTimeMillis() / 1000);
            toH5.put("nonceStr", "" + RandomStringUtils.randomAlphabetic(20));
            toH5.put("package", "prepay_id=" + object.get("prepay_id"));
            toH5.put("signType", "MD5");
            toH5.put("paySign", generateWeChatSign(toH5));
            return (JSONObject) JSONObject.toJSON(toH5);
        } else if (tradeType.equals("APP")) {
            JSONObject paramMap = new JSONObject();
            paramMap.put("appid", object.get("appid"));
            paramMap.put("partnerid", object.get("mch_id"));
            paramMap.put("noncestr", object.get("nonce_str"));
            paramMap.put("prepayid", object.get("prepay_id"));
            paramMap.put("package", "Sign=WXPay");
            paramMap.put("timestamp", StringUtils.substring(System.currentTimeMillis() + "", 0, 10));
            paramMap.put("sign",
                    generateWeChatSign( (Map<String,String>) JSON.parseObject(paramMap.toJSONString(),Map.class)));
//            paramMap.put("code_url", object.get("code_url"));
            return paramMap;
        } else if (tradeType.equals("NATIVE")) {
            JSONObject paramMap = new JSONObject();
            paramMap.put("code_url", object.get("code_url"));
            return paramMap;
        } else if (tradeType.equals("MWEB")) {
            JSONObject paramMap = new JSONObject();
            paramMap.put("mweb_url", object.get("mweb_url") + "&redirect_url=" + URLEncoder.encode(redirectUrl));
            return paramMap;
        } else {
            throw new ServiceException("unknow trade type.");
        }
    }

    private void addDefaultParam(Map<String, String> params) {
        params.put("appid", payConfigInfo.getAppid());
        params.put("mch_id", payConfigInfo.getMchId());
    }

    /**
     * 生成微信支付签名
     *
     * @param params 参数列表
     * @return 签名后的字符串
     */
    private String generateWeChatSign(Map<String, String> params) {
        String finalSign = "";
        // 1.generate StringA
        String stringA = "";
        List<String> paramkeys = new ArrayList<String>(params.keySet());
        Collections.sort(paramkeys);
        for (String key : paramkeys) {
            if (StringUtils.isNoneBlank(params.get(key)) && (!StringUtils.equals(key, "sign"))) {
                stringA = stringA + key + "=" + params.get(key) + "&";
            }
        }
        String stringSignTemp = stringA + "key=" + payConfigInfo.getSignKey();
        finalSign = DigestUtils.md5Hex(stringSignTemp).toUpperCase();
        return finalSign;
    }

    /**
     * 统一下单
     *
     * @param commodityName
     * @param attach
     * @param transactionSn
     * @param totalMoney
     * @param realIp
     * @param transactionStartTime
     * @param transactionExpireTime
     * @param notifyUrl
     * @param trade_type
     * @param openid
     * @return
     */
    public Map<String, String> unifiedOrder(String commodityName, String attach, String transactionSn, long
            totalMoney, String realIp, String transactionStartTime, String transactionExpireTime, String notifyUrl,
                                            String trade_type, String openid) {
        Map<String, String> params = new HashMap<String, String>();
        addDefaultParam(params);
        params.put("device_info", "WEB");
        params.put("nonce_str", RandomStringUtils.randomAlphanumeric(12));
        params.put("body", EmojiParser.removeAllEmojis(StringUtils.replacePattern(commodityName,"\\s+"," ")));
        params.put("attach", EmojiParser.removeAllEmojis(StringUtils.replacePattern(attach,"\\s+"," ")));
        params.put("out_trade_no", transactionSn);
        params.put("fee_type", "CNY");
        params.put("total_fee", totalMoney + "");
        params.put("spbill_create_ip", realIp);
        params.put("time_start", transactionStartTime);
        params.put("time_expire", transactionExpireTime);
        params.put("notify_url", notifyUrl);
        params.put("trade_type", trade_type);
        params.put("openid", openid);
        params.put("sign", generateWeChatSign(params));
        String body = WechatXMLConvertor.map2XML(params);
        try {
            String result = HttpClientUtil.buildPostRequest(unifiedOrder, 3).setBody(body).execute();
            Map<String, String> rv = WechatXMLConvertor.XML2Map(result);
            String returnMsg = rv.get("return_msg").toString();
            String return_code = rv.get("return_code").toString();
            if (StringUtils.equals(returnMsg, "OK") && StringUtils.equals(return_code, "SUCCESS")) {
                return rv;
            } else {
                throw new ServiceException(returnMsg);
            }
        } catch (HttpException httpExeption) {
            log.error("统一下单 ", httpExeption);
        }
        return null;
    }

    /**
     * 查询订单 https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
     *
     * @param transactionSn 我们的交易号
     * @return
     */
    @Override
    public Map<String, String> query(String transactionSn) {
        Map<String, String> params = new HashMap<String, String>();
        addDefaultParam(params);
        params.put("out_trade_no", transactionSn);
        params.put("nonce_str", RandomStringUtils.randomAlphanumeric(12));
        params.put("sign", generateWeChatSign(params));
        String body = WechatXMLConvertor.map2XML(params);
        try {
            String result = HttpClientUtil.buildPostRequest(orderQuery, 3).setBody(body).execute();//尝试3次
            return WechatXMLConvertor.XML2Map(result);
        } catch (HttpException httpExeption) {
            log.error("查询订单", httpExeption);
        }
        return null;
    }

    /**
     * 关闭订单
     * https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_3&index=5#
     *
     * @param transactionSn
     * @return
     */
    public String closeOrder(String transactionSn) {
        Map<String, String> params = new HashMap<String, String>();
        addDefaultParam(params);
        params.put("out_trade_no", transactionSn);// 商户订单号
        params.put("nonce_str", RandomStringUtils.randomAlphanumeric(12));
        params.put("sign", generateWeChatSign(params));
        String body = WechatXMLConvertor.map2XML(params);
        try {
            String result = HttpClientUtil.buildPostRequest(closeOrder).setBody(body).execute();
            return result;
        } catch (HttpException httpExeption) {
            log.error("关闭订单", httpExeption);
        }
        return null;
    }

    /**
     * 申请退款
     * https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_4&index=6
     *
     * @param transactionSn       我们的交易号
     * @param refundTransactionSn 我们的退款交易号
     * @param totalFee            交易的总金额
     * @param refoundTotalFee     退款的总金额
     * @return
     */
    @Override
    public RefundResponse refund(String transactionSn, String wechatTransactionId, String refundTransactionSn, long
            totalFee, long refoundTotalFee, String reason) {
        boolean useRecharge = false;
        Map<String, String> refundValue = refundTransaction(transactionSn, wechatTransactionId, refundTransactionSn,
                totalFee, refoundTotalFee, useRecharge, reason);
        String returnCode = refundValue.get("return_code");
        String resultCode = refundValue.get("result_code");
        if (StringUtils.equals(returnCode, "SUCCESS") && StringUtils.equals(resultCode, "SUCCESS")) {
            return RefundResponse.fromWechatPayResponse(refundValue);
        } else {
            String return_msg = refundValue.get("return_msg");
            String errorCode = refundValue.get("err_code");
            String errorDesc = refundValue.get("err_code_des");
            if (StringUtils.equalsIgnoreCase("NOTENOUGH", errorCode)) {//如果错误码是余额不足，自动换用资金池重试
                useRecharge = true;
                refundValue = refundTransaction(transactionSn, wechatTransactionId, refundTransactionSn, totalFee,
                        refoundTotalFee, useRecharge, reason);
                return RefundResponse.fromWechatPayResponse(refundValue);
            } else if (StringUtils.equalsIgnoreCase("ERROR", errorCode) && errorDesc != null
                    && StringUtils.equals("订单已全额退款", errorDesc)) {
                return RefundResponse.buildRepeatResponse(errorDesc);
            } else {
                return RefundResponse.buildExceptionResponse(return_msg);
            }
        }
    }


    private static org.joda.time.format.DateTimeFormatter WECHAT_DATE_FORMATTER = DateTimeFormat.forPattern
            ("yyyyMMddHHmmss");

    @Override
    public DateTime formatDate(String date) {
        return DateTime.parse(date, WECHAT_DATE_FORMATTER);
    }

    @Override
    public boolean checkSign(Map<String, String> callbackInputs) {
        String sign = generateWeChatSign(callbackInputs);
        return StringUtils.equals(sign, callbackInputs.get("sign"));
    }

    @Override
    public Optional<String> checkPayed(Transaction transaction, String addition) {
        Map<String, String> result = query(transaction.getTransactionSn());
        String return_code = result.get("return_code");// 请求结果
        String result_code = result.get("result_code");// 业务结果
        if (StringUtils.equals(return_code, "SUCCESS") && StringUtils.equals(result_code, "SUCCESS")) {
            String tradeStatus = result.get("trade_state");
            if (tradeStatus.equals(WeChatTradeStatus.SUCCESS.name())) {
                String third_sn = result.get("transaction_id");
                return Optional.of(third_sn);
            }
        }
        return Optional.empty();
    }

    private Map<String, String> refundTransaction(String transactionSn, String wechatTransactionId, String
            refundTransactionSn, long totalFee, long refoundTotalFee, boolean useRecharge, String reason) {
        Map<String, String> params = new HashMap<String, String>();
        addDefaultParam(params);
        params.put("nonce_str", RandomStringUtils.randomAlphanumeric(12));
        params.put("transaction_id", wechatTransactionId);
        params.put("out_trade_no", transactionSn);// 商户订单号
        params.put("out_refund_no", refundTransactionSn);// 内部的退款单号
        params.put("total_fee", String.valueOf(totalFee));
        params.put("refund_fee", String.valueOf(refoundTotalFee));
        params.put("refund_fee_type", "CNY");
        if (useRecharge) {
            params.put("refund_account", "REFUND_SOURCE_RECHARGE_FUNDS");
        }
        params.put("op_user_id", payConfigInfo.getMchId());
        params.put("sign", generateWeChatSign(params));
        String body = WechatXMLConvertor.map2XML(params);
        try {
            String result = HttpClientUtil.buildSSLPostRequest(refund, payConfigInfo.getSslKeyPath(), payConfigInfo
                    .getMchId()).setReadTimeOut(15000).setConnectTimeOut(2000).setBody(body).execute();
            Map<String, String> map = WechatXMLConvertor.XML2Map(result);
            return map;
        } catch (HttpException e) {
            log.error("fail to connect wechat gateway.", e);
            throw new ServiceException("退款请求异常");
        }
    }

    /**
     * 查询退款
     * https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_4&index=7
     *
     * @param orderSn 支付时的订单号
     * @return
     */
    @Override
    public List<RefundResult> refundQuery(String orderSn) {
        Map<String, String> params = new HashMap<String, String>();
        addDefaultParam(params);
        params.put("nonce_str", RandomStringUtils.randomAlphanumeric(12));
        params.put("out_trade_no", orderSn);
        params.put("sign", generateWeChatSign(params));
        String body = WechatXMLConvertor.map2XML(params);
        try {
            String result = HttpClientUtil.buildPostRequest(refundQuery).setBody(body).execute();
            return RefundResult.buildFromWechat(result);
        } catch (HttpException httpExeption) {
            log.error("查询退款", httpExeption);
            return Lists.newArrayList();
        }

    }

    @Override
    public Optional<String> transfer(String transferSn, String openId, String realName, int amount) {
        JSONObject object = transfer(transferSn, openId, "NO_CHECK", realName, amount);
        String thirdSn = null;
        if (object != null) {
            thirdSn = object.getString("payment_no");
        }
        return Optional.ofNullable(thirdSn);
    }


    // 转账接口,慎用
    private JSONObject transfer(String transferSn, String openid, String checkName, String reUserName, int amount) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mch_appid", payConfigInfo.getAppid());
        params.put("mchid", payConfigInfo.getMchId());
        params.put("nonce_str", RandomStringUtils.randomAlphanumeric(12));
        params.put("partner_trade_no", transferSn);// 我们的提现唯一编号
        params.put("openid", openid);
        params.put("check_name", checkName);// NO_CHECK,FORCE_CHECK,OPTION_CHECK
        params.put("re_user_name", reUserName);
        params.put("amount", String.valueOf(amount));
        params.put("spbill_create_ip", IpUtils.localhost());
        params.put("desc","余额提现,账户id:"+reUserName);
        params.put("sign", generateWeChatSign(params));
        String body = WechatXMLConvertor.map2XML(params);
        try {
            String result = HttpClientUtil.buildSSLPostRequest(transfers, payConfigInfo.getSslKeyPath(),
                    payConfigInfo.getMchId()).setReadTimeOut(15000).setConnectTimeOut(2000).setWaitTimeOut(2000)
                    .setBody(body).execute();
            Map<String, String> map = WechatXMLConvertor.XML2Map(result);
            String returnCode = map.get("return_code");
            String resultCode = map.get("result_code");
            if (StringUtils.equals(returnCode, "SUCCESS") && StringUtils.equals(resultCode, "SUCCESS")) {
                JSONObject resultJo = new JSONObject();
                resultJo.put("partner_trade_no", map.get("partner_trade_no"));
                resultJo.put("payment_no", map.get("payment_no"));
                resultJo.put("payment_time", map.get("payment_time"));
                return resultJo;
            } else {
                String err_code = map.get("err_code");
                String err_code_des = map.get("err_code_des");
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("err_code:").append(StringUtils.defaultString(err_code, "null")).append
                        ("err_code_des:").append(StringUtils.defaultString(err_code_des, "null"));
                throw new ServiceException(stringBuffer.toString());
            }
        } catch (HttpException httpExeption) {
            log.error("转账接口", httpExeption);
        }
        return null;
    }

    public String downloadBill(LocalDate date) {
        Map<String, String> params = new HashMap<String, String>();
        addDefaultParam(params);
        // params.put("device_info",);
        params.put("nonce_str", RandomStringUtils.randomAlphanumeric(12));
        params.put("bill_date", date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        params.put("bill_type", "ALL");
        params.put("sign", generateWeChatSign(params));
        String body = WechatXMLConvertor.map2XML(params);
        try {
            String result = HttpClientUtil.buildPostRequest(downloadbill).setReadTimeOut(20000).setConnectTimeOut
                    (10000).setBody(body).execute();
            if (result != null && result.contains("return_code")) {
                return null;
            } else {
                return result;
            }
        } catch (HttpException httpExeption) {
            log.error("", httpExeption);
        }
        return null;
    }


    public static String getWechatTimeStr(long currentTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new DateTime(currentTime).toDate());
    }

    public static Date formatWechatTime(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            return format.parse(time);
        } catch (Exception e) {
            return new Date();
        }

    }

    enum WeChatTradeStatus {

        SUCCESS(1, "支付成功"), REFUND(2, "转入退款"), NOTPAY(3, "未支付"), CLOSED(4, "已关闭"), REVOKED(5, "已撤销（刷卡支付）"),
        USERPAYING(6, "用户支付中"), PAYERROR(7, "支付失败(其他原因，如银行返回失败)");

        WeChatTradeStatus(int code, String name) {
            this.code = code;
            this.name = name;
        }

        private int code;
        private String name;

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        static Map<Integer, WeChatTradeStatus> allMap = new HashMap<Integer, WeChatTradeStatus>();

        static {
            for (final WeChatTradeStatus status : EnumSet.allOf(WeChatTradeStatus.class)) {
                allMap.put(status.getCode(), status);
            }
        }

        public static WeChatTradeStatus getByCode(Integer type) {
            return allMap.get(type);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }

    }
    @Override
    public Optional<String> qrpay(String commodityName, String attach, String transactionSn,
                                  int totalMoney, String realIp, Date startTime, Date expireTime) {
        Map<String, String> object = this.unifiedOrder(commodityName, attach, transactionSn,
                totalMoney, realIp, getWechatTimeStr(startTime.getTime()), getWechatTimeStr(expireTime.getTime()), payConfigInfo.getNotifyUrl(), "NATIVE","");

        return Optional.of(""+object.get("code_url"));
    }

}
