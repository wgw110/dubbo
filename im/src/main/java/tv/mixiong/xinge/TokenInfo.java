package tv.mixiong.xinge;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * .
 */
public class TokenInfo {
    @JSONField(name = "msgsNum")
    private Long msgsNum;

    @JSONField(name = "isReg")
    private int isReg;

    @JSONField(name = "connTimestamp")
    private Long connTimestamp;

    public Long getMsgsNum() {
        return msgsNum;
    }

    public void setMsgsNum(Long msgsNum) {
        this.msgsNum = msgsNum;
    }

    public int getIsReg() {
        return isReg;
    }

    public void setIsReg(int isReg) {
        this.isReg = isReg;
    }

    public Long getConnTimestamp() {
        return connTimestamp;
    }

    public void setConnTimestamp(Long connTimestamp) {
        this.connTimestamp = connTimestamp;
    }
}
