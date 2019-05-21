package tv.mixiong.saas.commodity.dao;

import org.springframework.stereotype.Repository;
import tv.mixiong.saas.commodity.entity.Discount;

import java.util.Date;

@Repository
public class DiscountDao{

    public Discount get(int commodityID) {
        return new Discount();
    }

    public Discount addOrUpdateDiscount(int commodityId,String name, double discountRate, Date startTime, Date endTime) {
       return new Discount();
    }

    public void removeDiscount(int commodityId) {

    }
}
