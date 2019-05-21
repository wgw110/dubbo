package tv.mixiong.saas.transfer.dao;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;
import tv.mixiong.saas.transfer.entity.TransferBind;

import java.util.List;

@Repository
public class TransferBindDao{

    public Long add(TransferBind transferAccount) {
       return 0L;
    }

    public TransferBind findByPassport(String passport, String paymethod) {
        return null;
    }

    public List<TransferBind> findByPassport(String passport) {
       return Lists.newArrayList();
    }
}
