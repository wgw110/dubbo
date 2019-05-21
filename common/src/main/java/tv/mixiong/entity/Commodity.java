package tv.mixiong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "commodity")
@Entity
@Builder
public class Commodity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer plat;

    /**
     * 实时售卖价格
     */
    private Integer price;

    /**
     * 原价
     */
    private Integer originPrice;

    /**
     * 拼团或砍价显示的价格
     */
    private Integer marketPrice;

    private String schoolId;

    private Integer commodityType;

    private Integer marketType;

    private Long attachId;

    private Integer onSale;

    private Long createTime;

    private Long updateTime;

    /**
     * 秒杀开始时间
     */
    private Long startSaleTime;

    /**
     * 秒杀结束时间
     */
    private Long endSaleTime;

    private Integer vipDiscountRate;

}
