package tv.mixiong.saas.account.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import tv.mixiong.saas.account.constants.MiCoinType;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MiCoinRecord {

    private int id;
    private String passport;
    private double amount;
    private double balance;
    private int type;
    private String transaction_sn;
    private String commodity_id;
    private Date updateTime;
    private String info;
    private int sid;

    public static MiCoinRecord record(String passport, MiCoinType type, String info, String transaction_sn, Integer amount, Long commodityId) {
        MiCoinRecord miCoinRecord = new MiCoinRecord();
        miCoinRecord.setPassport(passport);
        miCoinRecord.setAmount(amount);
        miCoinRecord.setType(type.getCode());
        miCoinRecord.setCommodity_id(String.valueOf(commodityId));
        miCoinRecord.setTransaction_sn(transaction_sn);
        miCoinRecord.setInfo(info);
        return miCoinRecord;
    }

    public static MiCoinRecord recordByAmount(String passport, double amount, MiCoinType type, String commodity_id, String transaction_sn) {
        MiCoinRecord miCoinRecord = new MiCoinRecord();
        miCoinRecord.setPassport(passport);
        miCoinRecord.setAmount(amount);
        miCoinRecord.setType(type.getCode());
        miCoinRecord.setCommodity_id(commodity_id);
        miCoinRecord.setTransaction_sn(transaction_sn);
        miCoinRecord.setInfo(null);
        return miCoinRecord;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
