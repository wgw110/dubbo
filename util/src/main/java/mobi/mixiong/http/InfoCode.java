package mobi.mixiong.http;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回
 * Created by huiwu on 2014 下午6:42:27
 *
 */
public enum InfoCode {

    SUCCESS (200,"OK"),

    FAIL (50000,"操作失败，请重试"),

    URL_NOT_FOUND (40000,"url不存在"),
    
    PARAM_NOT_FOUND (40004,"缺少参数"),
    
    USER_LOGOUT (40006,"请登录后重试"),
	
	OPERATION_NOT_ALLOWED(30000,"未经授权的操作");
	

    private int status;

    private String msg;

    private InfoCode(int status,String msg){
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }


    public static final Map<Integer,InfoCode> lookupByStatus=new HashMap<Integer, InfoCode>();

    static {
        for(final InfoCode p: EnumSet.allOf(InfoCode.class)){
            lookupByStatus.put(p.getStatus(),p);
        }
    }

    public static InfoCode getInfoCode(int status){
        return lookupByStatus.get(status);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
