package tv.mixiong.saas.thirdpay.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundResponse {
    private int status;
    private String code;
    private String errorMsg;
    /**
     * 需要退款的订单号
     */
    private String refundThirdSn;
    private String thirdId;
    /**
     * 退款产生的订单号
     */
    private String refundSn;
    private boolean hasMoneyChanged;
    private long refundFee;
    private Long refundTime;

    public static RefundResponse buildExceptionResponse(String errorMsg) {
        RefundResponse response = new RefundResponse();
        response.setStatus(RefundStatus.FAIL.getStatus());
        response.setErrorMsg(errorMsg);
        return response;
    }


    public static RefundResponse buildRepeatResponse(String errorMsg) {
        RefundResponse response = new RefundResponse();
        response.setStatus(RefundStatus.REPEAT.getStatus());
        response.setErrorMsg(errorMsg);
        return response;
    }

    public static RefundResponse fromWechatPayResponse(Map<String, String> wechatRefundMap) {
        RefundResponse response = new RefundResponse();
        String returnCode = wechatRefundMap.get("return_code");
        String resultCode = wechatRefundMap.get("result_code");
        //相关逻辑处理
        if (StringUtils.equals(returnCode, WechatPayConstants.CODE_SUCCESS) && StringUtils.equals(resultCode, WechatPayConstants.CODE_SUCCESS)) {
            String thirdSn = wechatRefundMap.get("refund_id");
            response.setRefundThirdSn(thirdSn);
            response.setStatus(RefundStatus.SUCCESS.getStatus());

            response.setRefundSn(wechatRefundMap.get("out_refund_no"));
            int refundFee = Integer.parseInt(wechatRefundMap.get("refund_fee"));
            response.setHasMoneyChanged(refundFee > 0);
            response.setRefundFee(refundFee);
        } else {
            response.setStatus(RefundStatus.FAIL.getStatus());
            response.setErrorMsg(wechatRefundMap.get("err_code_des"));
            response.setCode("err_code");
        }

        return response;
    }
}
