package tv.mixiong.saas.commodity.dao;

import org.springframework.stereotype.Repository;
import tv.mixiong.saas.commodity.entity.DiscountHistory;
import tv.mixiong.saas.commodity.entity.DiscountLimit;


@Repository
public class DiscountLimitDao{

    public DiscountLimit get(int commodityID) {
        return new DiscountLimit();
    }

    public DiscountLimit addOrUpdateDiscount(int commodityID,String name, double discount_rate, int total_amount) {
       return new DiscountLimit();
    }

    public int use(int commodity_id){
        return 0;
    }

    public void release(int commodity_id){
    }

    public void addDiscountHistory(String passport, int commodity_id) {
    }

    public void updateDiscountHistory(String passport,int commodity_id,String orderSn){

    }


    public DiscountHistory findDiscountHistory(String passport, int commodity_id){
        return new DiscountHistory();
    }

    public void releaseDiscountHistory(String passport, int commodity_id){
    }

    public void removeDiscount(int commodityId) {
    }
}
