package tv.mixiong.ds.dao.im;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tv.mixiong.dao.CustomizedMapper;
import tv.mixiong.ds.dao.entity.ImUserSign;

@Repository
public interface ImUserSignDao extends CustomizedMapper<ImUserSign> {

    @Select("select * from mx_im_user_sign where sdk_app_id =  #{sdk_app_id} and user_name = #{user_name} and status= #{status}")
    ImUserSign findBySdkAppIdAndUserNameAndStatus(@Param("sdk_app_id") String appId, @Param("user_name") String name, @Param("status") int status);

    @Update("update mx_im_user_sign set status=2 where sdk_app_id = #{sdk_app_id}  and user_name = #{user_name}")
    void invalidUserSign(@Param("sdk_app_id") String appId, @Param("user_name") String name);

}
