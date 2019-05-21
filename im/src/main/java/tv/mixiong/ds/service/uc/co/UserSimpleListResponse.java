package tv.mixiong.ds.service.uc.co;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * -------------------------------------
 * Author: wuzbin
 * Time  : 2017-03-17 下午3:05
 * -------------------------------------
 */
public class UserSimpleListResponse {
    @JSONField(name = "data")
    private List<UserProfileVo> users;

    public List<UserProfileVo> getUsers() {
        return users;
    }

    public void setUsers(List<UserProfileVo> users) {
        this.users = users;
    }
}
