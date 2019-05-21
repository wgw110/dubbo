package tv.mixiong.saas.thirdpay.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tv.mixiong.dao.CustomizedMapper;
import tv.mixiong.saas.thirdpay.entity.WechatPayConfig;

public interface WechatPayConfigDao extends CustomizedMapper<WechatPayConfig> {

    @Select("select * from wechat_pay_config where school_id = #{school_id}")
    WechatPayConfig getWechatPayConfigBySchool(@Param("school_id") String schoolId) ;
}
