package tv.mixiong.ds.dao.push;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tv.mixiong.dao.CustomizedMapper;
import tv.mixiong.ds.dao.entity.UserPush;

import java.util.List;

@Repository
public interface UserPushDao extends CustomizedMapper<UserPush> {

    @Select("select * mx_user_push where product_id = #{productId} and passport in (#{passports}) and status = #{status}")
    List<UserPush> findByProductIdAndPassportInAndStatus(@Param("productId") Integer productId,@Param("passports") List<String> passports,@Param("status") int status);

}
