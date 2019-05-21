package tv.mixiong.saas.account.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tv.mixiong.dao.CustomizedMapper;
import tv.mixiong.saas.account.entity.Wallet;

import java.util.List;

@Repository
public interface WalletDao extends CustomizedMapper<Wallet> {

    @Select("<script>" +
            "  select * from wallet where identity = #{identity}" +
            "  <if test=\"start_time != null and start_time > 0 \">" +
            "     and update_time &gt;= #{start_time}" +
            "  </if>" +
            "  <if test=\"end_time != null and end_time > 0\">" +
            "     and update_time &lt;= #{end_time}" +
            "  </if>" +
            "  <if test=\"type != null and type >0\">" +
            "     and type = #{type}" +
            "  </if>" +
            "  order by id desc " +
            "</script>")
    List<Wallet> listWallet(@Param("identity") String identity,@Param("start_time")Long startTime,
                            @Param("end_time")Long endTime,@Param("type")Integer type);

    @Select("select balance from wallet where identity =#{identity}")
    int getBalance(@Param("identity") String identity);

    @Select("select * from wallet where identity = #{identity} and sid = 0 ")
    Wallet getLastedRecord(String identity) ;

    @Update("update wallet set balance = #{balance},sid = 0  where identity = #{identity} and id = #{id}")
    Integer updateLastedRecord(@Param("identity") String identity, @Param("id") Long lastedId, @Param("balance") Integer newBalance);

    @Update("update wallet set sid = #{sid} where identity = #{identity} and id = #{id}")
    int updateParentRecord(@Param("identity") String identity, @Param("id") Long parentId, @Param("sid") Long sid) ;
}
