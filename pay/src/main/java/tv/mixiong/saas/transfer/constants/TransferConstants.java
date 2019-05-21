package tv.mixiong.saas.transfer.constants;

import java.util.HashMap;
import java.util.Map;

public class TransferConstants {

    public static final Map<TransferMethod,Float> TRANSFER_PERCENTAGE=new HashMap<TransferMethod,Float>();
    public static final float TRANSFER_COPYRIGHT=0.01f;
    public static final Map<TransferMethod,Integer> TRANSFER_MIN_MONEY =new HashMap<TransferMethod,Integer>();
    public static final Map<TransferMethod,Integer> TRANSFER_MAX_MONEY =new HashMap<TransferMethod,Integer>();
    public static final Map<TransferMethod,Integer> TRANSFER_MAX_MONEY_ONE_DAY =new HashMap<TransferMethod,Integer>();
    static {
        TRANSFER_PERCENTAGE.put(TransferMethod.WECHAT_APP,0.02f);
        TRANSFER_PERCENTAGE.put(TransferMethod.WECHAT_JS,0.02f);
        TRANSFER_PERCENTAGE.put(TransferMethod.TOBUSINESS,0.02f);
        TRANSFER_PERCENTAGE.put(TransferMethod.ALIPAY_APP,0.02f);
        
        TRANSFER_MIN_MONEY.put(TransferMethod.WECHAT_APP,200);
        TRANSFER_MIN_MONEY.put(TransferMethod.WECHAT_JS,200);
        TRANSFER_MIN_MONEY.put(TransferMethod.ALIPAY_APP,200);

        TRANSFER_MAX_MONEY.put(TransferMethod.WECHAT_APP,2000000);
        TRANSFER_MAX_MONEY.put(TransferMethod.WECHAT_JS,2000000);
        TRANSFER_MAX_MONEY.put(TransferMethod.ALIPAY_APP,2000000);


        TRANSFER_MAX_MONEY_ONE_DAY.put(TransferMethod.WECHAT_APP,2000000);
        TRANSFER_MAX_MONEY_ONE_DAY.put(TransferMethod.WECHAT_JS,2000000);
        TRANSFER_MAX_MONEY_ONE_DAY.put(TransferMethod.ALIPAY_APP,2000000);

    }

}
