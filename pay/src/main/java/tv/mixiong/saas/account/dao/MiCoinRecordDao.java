package tv.mixiong.saas.account.dao;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;
import tv.mixiong.saas.account.entity.MiCoinRecord;

import java.util.List;

@Repository
public class MiCoinRecordDao {

    /**
     * 添加米币记录
     *
     * @param record
     * @return
     */
    public Long addMiCoinRecord(MiCoinRecord record) {
        return 0L;
    }


    /**
     * 分页
     *
     * @param passport
     * @param offset
     * @param size
     * @return
     */
    public List<MiCoinRecord> listByPage(String passport, int offset, int size) {
        return Lists.newArrayList();
    }

    /**
     * 获取父记录
     *
     * @param passport
     * @return
     */
    public MiCoinRecord getLastedRecord(String passport) {
        return new MiCoinRecord();
    }

    /**
     * 更新当前记录为最新记录
     *
     * @param passport
     * @param lastedId
     * @param newBalance
     * @return
     */
    public int updateLastedRecord(String passport, int lastedId,
                                  double newBalance) {
        return 0;
    }

    /**
     * 更新父记录信息
     *
     * @param passport
     * @param parentId
     * @param sid
     * @return
     */
    public int updateParentRecord(String passport, int parentId, int sid) {
        return 0;
    }

}
