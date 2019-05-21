package tv.mixiong.saas.commodity.dao;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tv.mixiong.dao.CustomizedMapper;
import tv.mixiong.dao.SimpleSelectInExtendedLanguageDriver;
import tv.mixiong.entity.Commodity;

import java.util.List;


public interface CommodityDao extends CustomizedMapper<Commodity> {

    @Select("select * from commodity where commodity_type = #{commodity_type} and attach_id = #{attach_id}")
    Commodity findByTypeAndAttachId(@Param("commodity_type") Integer commodityType, @Param("attach_id") Long attachId);

    @Update("update commodity set on_sale = #{on_sale} , update_time = #{update_time} where id = #{id}")
    Integer updateSaleStatus(@Param("id") Long id, @Param("on_sale") Integer onSale, @Param("update_time") Long updateTime);

    @Lang(SimpleSelectInExtendedLanguageDriver.class)
    @Select("select * from commodity where school_id = #{school_id} and commodity_type in (#{types}) order by id desc")
    List<Commodity> listCommodity(@Param("school_id") String schoolId, @Param("types") List<Integer> commodityTypes);

    @Select("select * from commodity where id in (#{ids})")
    @Lang(SimpleSelectInExtendedLanguageDriver.class)
    List<Commodity> listCommodityByIds(@Param("ids") List<Long> commodityIds) ;
}
