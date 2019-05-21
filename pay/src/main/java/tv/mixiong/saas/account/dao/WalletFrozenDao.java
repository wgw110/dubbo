package tv.mixiong.saas.account.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tv.mixiong.dao.CustomizedMapper;
import tv.mixiong.saas.account.entity.walletFrozen;

public interface WalletFrozenDao extends CustomizedMapper<walletFrozen> {

    @Select("select frozen_balance from wallet_frozen where identity = #{identity}")
    int getFrozenBalance(@Param("identity") String identity);

    @Select("select * from wallet_frozen where identity =#{identity} and sid = 0")
    walletFrozen getLastRecord(@Param("identity") String identity);

}
