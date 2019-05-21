package tv.mixiong.saas.pay.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tv.mixiong.dao.CustomizedMapper;
import tv.mixiong.saas.pay.entity.OrderPayDetail;

public interface OrderPayDetailDao extends CustomizedMapper<OrderPayDetail> {

    @Select("select * from order_pay_detail where order_sn = #{order_sn}")
    OrderPayDetail getBySn(@Param("order_sn") String orderSn);
}
