package tv.mixiong.saas.transfer.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tv.mixiong.dao.CustomizedMapper;
import tv.mixiong.saas.transfer.entity.Transfer;

import java.util.List;

public interface TransferDao extends CustomizedMapper<Transfer> {

    @Select("select * from transfer where transfer_sn = #{transfer_sn}")
    Transfer getTransferBySn(@Param("transfer_sn") String transfer_sn);

    /**
     * 只有转账成功才更新end_time
     *
     * @param transferSn
     * @param transferStatus
     * @param thirdSn
     * @param currTime
     * @return
     */
    @Update("<script> " +
            " update transfer set transfer_status = #{transfer_status},update_time = #{curr_time} " +
            " <if test='third_sn != \"\" and third_sn != null'> " +
            "   ,third_sn = #{third_sn} " +
            " </if> " +
            " <if test='transfer_status =2'> " +
            "   ,end_time = #{curr_time} " +
            " </if> " +
            " where transfer_sn = #{transfer_sn} " +
            "</script>")
    Integer updateTransferStatus(@Param("transfer_sn") String transferSn, @Param("transfer_status") Integer transferStatus,
                                 @Param("third_sn") String thirdSn, @Param("curr_time") Long currTime);

    @Select("<script> " +
            " select * from transfer where identity = #{identity} " +
            " <if test='start_time != null'> " +
            "  and create_time &gt;= #{start_time} " +
            " </if>  " +
            " <if test='end_time != null'> " +
            "  and create_time &lt;= #{end_time} " +
            " </if> " +
            " order by create_time desc " +
            "</script>")
    List<Transfer> listTransfer(@Param("identity") String identity, @Param("start_time") Long startTime, @Param("end_time") Long endTime);
}
