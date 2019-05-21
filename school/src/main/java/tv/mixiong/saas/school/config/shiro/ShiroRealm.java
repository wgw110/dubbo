package tv.mixiong.saas.school.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import tv.mixiong.saas.school.cache.AuthCodeCache;
import tv.mixiong.saas.school.constants.AuthCodeTypeEnum;
import tv.mixiong.saas.school.constants.LoginWhiteList;
import tv.mixiong.saas.school.ds.AuthDs;
import tv.mixiong.saas.school.entity.User;


@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private AuthDs authDs;

    @Autowired
    private AuthCodeCache authCodeCache;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userId = (String) principalCollection.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        ManagerDto managerDto = managerDs.getByUser(userName);
//        if (managerDto != null) {
//            String roleName = ManagerRole.getNameByValue(managerDto.getRole());
//            if (roleName != null) {
//                info.addRole(roleName);
//            }
//        }

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws
            AuthenticationException {
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String mobile = token.getUsername();
        String authCode = new String(token.getPassword());
        String nation = token.getHost();

        String cacheKey = AuthCodeCache.buildCacheKey(mobile, AuthCodeTypeEnum.LOGIN);
        String realCode = authCodeCache.get(cacheKey);
        if ((!StringUtils.equals(authCode, realCode)) && (!LoginWhiteList.getWhiteMobile().contains(mobile))) {
            throw new IncorrectCredentialsException("验证码错误");
        } else {
            authCodeCache.drop(cacheKey);
        }

        User user = authDs.findByMobile(nation, mobile);
        if (user == null) {
            user = authDs.createUserByMobile(nation, mobile);
        }

        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
        SimplePrincipalCollection principalCollection = new SimplePrincipalCollection(user.getId(), user.getMobile());
        ((SimpleAuthenticationInfo) authenticationInfo).setPrincipals(principalCollection);
        return authenticationInfo;
    }
}
