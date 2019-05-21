package tv.mixiong.dubbo.service;

import java.util.Optional;

/**
 * 登录
 * 权限
 * 短信
 * session check
 */
public interface IAuthService {

     Boolean login(Optional<String> nation, String mobile, String authCode, String uid);


     void saveUser();

}
