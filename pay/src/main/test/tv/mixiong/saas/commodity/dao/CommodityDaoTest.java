package tv.mixiong.saas.commodity.dao;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tv.mixiong.entity.Commodity;
import tv.mixiong.saas.commodity.constants.OnsaleStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("develop")
@SpringBootTest
public class CommodityDaoTest {

    @Autowired
    private CommodityDao commodityDao;

    @Test
    public void insert(){
        Commodity commodity = Commodity.builder().name("测试商品").plat(1).price(100).originPrice(110).schoolId("2")
                .commodityType(1).marketType(1).attachId(2L).onSale(0).createTime(System.currentTimeMillis())
                .updateTime(System.currentTimeMillis()).startSaleTime(System.currentTimeMillis())
                .endSaleTime(System.currentTimeMillis()).vipDiscountRate(90).build();
        commodityDao.insertUseGeneratedKeys(commodity);
        System.out.println(commodity);
    }

    @Test
    public void findByTypeAndAttachId() {
        System.out.println(commodityDao.findByTypeAndAttachId(1,2L));
    }

    @Test
    public void updateSaleStatus() {
        System.out.println(commodityDao.updateSaleStatus(1L,OnsaleStatus.ON_SALE.getCode(),System.currentTimeMillis()));
    }

    @Test
    public void listCommodity() {
        System.out.println(commodityDao.listCommodity("2",Lists.newArrayList(1)));
    }

    @Test
    public void listCommodityByIds() {
        System.out.println(commodityDao.listCommodityByIds(Lists.newArrayList(1L)));
    }
}