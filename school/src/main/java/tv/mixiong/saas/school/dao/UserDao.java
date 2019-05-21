package tv.mixiong.saas.school.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tv.mixiong.dao.CustomizedMapper;
import tv.mixiong.saas.school.entity.User;

@Repository
public interface UserDao extends CustomizedMapper<User> {
    @Select("select * from user where nation = #{nation} and mobile = #{mobile}")
    User findByMobile(@Param("nation") String nation, @Param("mobile") String mobile);
}
