package tv.mixiong.saas.school.cache;

import mobi.mixiong.cache.BaseHashCache;
import org.springframework.stereotype.Component;

@Component
public class UserSessionCache extends BaseHashCache<String,String, String> {
    @Override
    protected String getPrefix() {
        return "saas:user:session";
    }

    @Override
    protected Long getTTL() {
        return 30 * 24 * 3600L;
    }
}
