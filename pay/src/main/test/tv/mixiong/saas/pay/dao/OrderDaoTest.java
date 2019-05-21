package tv.mixiong.saas.pay.dao;

import com.google.common.collect.Lists;
import mobi.mixiong.util.IpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tv.mixiong.saas.pay.constants.OrderStatus;
import tv.mixiong.saas.pay.entity.Order;
import tv.mixiong.saas.pay.util.SNGenerator;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("develop")
@SpringBootTest
public class OrderDaoTest {

    @Autowired
    private OrderDao orderDao;

    @Test
    public void insert(){
        Long time = System.currentTimeMillis();
        Order order = Order.builder().passport("214799").orderSn(SNGenerator.generateOrderSnByCommodityId(1))
                .commodityId(1L).amount(1).totalMoney(100).createTime(time).updateTime(time).expireTime(time)
                .orderStatus(1).ip(IpUtils.localhost()).memo("memo").serviceCallback("callback")
                .couponSn("couponSn").plat(1).build();
        orderDao.insertUseGeneratedKeys(order) ;
        System.out.println(order);
    }

    @Test
    public void getOrder()  {
        Order order = orderDao.getOrder("190313200943UHGUCJ0182");
        System.out.println(order);
    }

    @Test
    public void updateStatus() {
        orderDao.updateStatus("190313200943UHGUCJ0182",3,System.currentTimeMillis());
    }

    @Test
    public void listOrderByCommodityIdAndStatus() {
        orderDao.listOrderByCommodityIdAndStatus(1L,1);

    }

    @Test
    public void updateStatusWithWriteLock() {
        List<Integer> canLockStatus = Lists.newArrayList(OrderStatus.LOCKED.getCode(), OrderStatus.CREATE.getCode(), OrderStatus.REMOVED.getCode(), OrderStatus.CANCELED.getCode());
        int size = orderDao.updateStatusWithWriteLock("190313200943UHGUCJ0182", System.currentTimeMillis(), canLockStatus);
        System.out.println(size);


    }

    @Test
    public void listExpireOrder() {
        List<Order> orders = orderDao.listExpireOrder(System.currentTimeMillis());
        System.out.println(orders);
    }
}