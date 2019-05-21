package tv.mixiong.saas.account.dao;

import org.springframework.stereotype.Repository;

@Repository
public class MiCoinDao {

    /**
     * 消费米币
     * 仅用于购买商品支付，需要账户余额大于商品值
     *
     * @param passportFrom
     * @param price
     * @return
     */
    public boolean consumeCoin(String passportFrom, float price) {
        return false;
    }

    /**
     * 增加米币
     *
     * @param passport
     * @param price
     * @return
     */
    public boolean addCoin(String passport, float price) {

        return false;

    }

    /**
     * 消费米币
     * 用于减少账号米币，可以扣成负数
     *
     * @param passport
     * @param price
     * @return
     */
    public boolean reduceCoin(String passport, float price) {
        return false;

    }

    public float queryCoin(String passport) {
        return 0;
    }
}
