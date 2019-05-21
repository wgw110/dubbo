package tv.mixiong.saas.account.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MiCoin {

    private int id;
    private String passport;
    private float balance;
    private Date updateTime;
}
