package tv.mixiong.saas.account.ds;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tv.mixiong.saas.account.dao.WalletDao;
import tv.mixiong.saas.account.entity.Wallet;

@Component
public class WalletDs {

    @Autowired
    WalletDao walletDao;

    /**
     * @param wallet
     * @param code 1 不改变钱包余额， 2 改变钱包余额
     */
    @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class,propagation = Propagation.NESTED)
    public void addWallet(Wallet wallet,int code){
        String identity = wallet.getIdentity();
        Wallet lastedRecord = walletDao.getLastedRecord(identity);
        //当前没有该用户钱包记录
        if(lastedRecord == null){
            wallet.setSid(0);
            int balance = code == 1 ? 0 : wallet.getAmount();
            wallet.setBalance(balance);
            walletDao.insertUseGeneratedKeys(wallet);
            Wallet currWallet = walletDao.getLastedRecord(identity);
            Preconditions.checkArgument(wallet.getId().equals( currWallet.getId()));
        }else {
            wallet.setSid(-1);
            walletDao.insertUseGeneratedKeys(wallet);
            int size1 = walletDao.updateParentRecord(identity, lastedRecord.getId(), wallet.getId());
            Preconditions.checkArgument(size1 ==1,"unexpected size");
            int balance = code == 1 ? lastedRecord.getBalance() : wallet.getAmount() + lastedRecord.getBalance();
            Integer size2 = walletDao.updateLastedRecord(identity, wallet.getId(), balance);
            Preconditions.checkArgument(size2 ==1,"unexpected size");
        }
    }
}
