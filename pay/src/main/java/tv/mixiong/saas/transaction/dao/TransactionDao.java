package tv.mixiong.saas.transaction.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tv.mixiong.dao.CustomizedMapper;
import tv.mixiong.saas.transaction.entity.Transaction;

import java.util.List;

public interface TransactionDao extends CustomizedMapper<Transaction> {

    @Select("select * from transaction where transaction_sn = #{transaction_sn}")
    Transaction getTransactionBySn(@Param("transaction_sn") String transactionSn);

    /***
     * 只有交易类型不为支付，并且状态不为取消状态才进行end_time 的更新
     * @param orderSn
     * @param transactionSn
     * @param transactionType
     * @param thirdSn
     * @param status
     * @return
     */
    @Update("<script>" +
            "  update transaction set transaction_status = #{transaction_status},update_time = #{curr_time}" +
            "  <if test='third_sn != null and third_sn != \"\" ' >" +
            "     ,third_sn = #{third_sn}" +
            "  </if>" +
            "  <if test='transaction_status !=3 and transaction_type != 1' >" +
            "     ,end_time = #{curr_time}" +
            "  </if>" +
            "  where order_sn = #{order_sn} and transaction_sn = #{transaction_sn} and transaction_type = #{transaction_type}" +
            "</script>")
    Integer updateTransactionStatus(@Param("order_sn") String orderSn, @Param("transaction_sn") String transactionSn,
                                    @Param("transaction_type") int transactionType, @Param("third_sn") String thirdSn,
                                    @Param("transaction_status") int status,@Param("curr_time")Long currTime);

    @Select("<script>" +
            " select * from transaction where order_sn =#{order_sn} " +
            " <if test='transaction_type != null and transaction_type >0'> " +
            "  and transaction_type = #{transaction_type} " +
            " </if> " +
            "</script>")
    List<Transaction> listTransactionByOrderSnAndType(@Param("order_sn") String orderSn,@Param("transaction_type") Integer type);

    @Update("update transaction set memo = #{memo} where transaction_sn=#{transaction_sn}")
    int updateMemo(@Param("transaction_sn") String transactionSn, @Param("memo") String memo);
}
