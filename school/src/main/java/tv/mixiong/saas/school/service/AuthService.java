package tv.mixiong.saas.school.service;

import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import tv.mixiong.dubbo.service.IAuthService;
import tv.mixiong.dubbo.exception.ServiceException;
import tv.mixiong.saas.school.ds.AuthDs;

import java.util.Optional;

@Slf4j
@Service(interfaceClass = IAuthService.class)
public class AuthService implements IAuthService {

    @Autowired
    AuthDs authDs;

    @Override
    public Boolean login(final Optional<String> nation, String mobile, final String authCode, final String uid) {
        Subject subject = SecurityUtils.getSubject();
        try {
            mobile = StringUtils.removeStart(mobile, "0");
            subject.login(new UsernamePasswordToken(mobile, authCode, nation.orElse("86")));
        } catch (IncorrectCredentialsException e) {
            throw new ServiceException("验证码错误");
        } catch (Exception e) {
            log.error("fail login", e);
            throw new ServiceException("系统错误");
        }
        return true;
    }

    @Override
    public void saveUser() {
        authDs.user_transaction();
    }
}
