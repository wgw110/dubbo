package tv.mixiong.ds.service.uc.co;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;


/**
 * Created by wuzbin on 16/5/27.
 */
public class UserListResponse {
    @JSONField(name = "data")
    private List<UserDetailProfileVo> users;

    public List<UserDetailProfileVo> getUsers() {
        return users;
    }

    public void setUsers(List<UserDetailProfileVo> users) {
        this.users = users;
    }
}
