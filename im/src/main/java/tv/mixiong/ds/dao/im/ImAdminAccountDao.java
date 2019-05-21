package tv.mixiong.ds.dao.im;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tv.mixiong.dao.CustomizedMapper;
import tv.mixiong.ds.dao.entity.ImAdminAccount;

@Repository
public interface ImAdminAccountDao extends CustomizedMapper<ImAdminAccount> {

    @Select("select * from mx_im_admin_account where product_id =  #{productId}")
    ImAdminAccount findByProductId(@Param("productId") int productId);
}
