package tv.mixiong.saas.pay.dao;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tv.mixiong.dao.CustomizedMapper;
import tv.mixiong.dao.SimpleSelectInExtendedLanguageDriver;
import tv.mixiong.saas.pay.entity.Order;

import java.util.List;

public interface OrderDao extends CustomizedMapper<Order> {

    @Select("select * from `order` where order_sn = #{order_sn}")
    Order getOrder(@Param("order_sn") String orderSn);

    @Update("update `order` set order_status = #{status} , update_time= #{update_time} where order_sn= #{order_sn}")
    int updateStatus(@Param("order_sn") String orderSn, @Param("status") int status, @Param("update_time") Long updateTime);

    @Select("select * from `order` where commodity_id= #{commodity_id} and order_status= #{status}")
    List<Order> listOrderByCommodityIdAndStatus(@Param("commodity_id") Long id, @Param("status") int status);

    @Lang(SimpleSelectInExtendedLanguageDriver.class)
    @Update("update `order` set order_status=6 , update_time= #{update_time} where order_sn= #{order_sn} and order_status in(#{status_list})")
    int updateStatusWithWriteLock(@Param("order_sn") String orderSn, @Param("upd" +
            "ate_time") Long updateTime, @Param("status_list") List<Integer> statusList);

    @Select("select * from `order` where order_status=1 and expire_time<=#{curr_time}")
    List<Order> listExpireOrder(@Param("curr_time") Long currTime);
}
