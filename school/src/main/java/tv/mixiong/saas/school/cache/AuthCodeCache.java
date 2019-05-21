package tv.mixiong.saas.school.cache;

import mobi.mixiong.cache.BaseValueCache;
import org.springframework.stereotype.Repository;
import tv.mixiong.saas.school.constants.AuthCodeTypeEnum;

@Repository
public class AuthCodeCache extends BaseValueCache<String, String> {
    @Override
    protected String getPrefix() {
        return "saas:authcode";
    }

    @Override
    protected Long getTTL() {
        return 300L;
    }

    public static  String buildCacheKey(String mobile, AuthCodeTypeEnum type) {
        return mobile + ":" + type.name();
    }
}
