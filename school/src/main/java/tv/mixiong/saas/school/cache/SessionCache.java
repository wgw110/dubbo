package tv.mixiong.saas.school.cache;

import mobi.mixiong.cache.BaseValueCache;
import org.springframework.stereotype.Component;
import tv.mixiong.saas.school.entity.SessionVo;

@Component
public class SessionCache extends BaseValueCache<String, SessionVo> {
    @Override
    protected String getPrefix() {
        return "saas:session:";
    }

    @Override
    protected Long getTTL() {
        return 6 * 3600L;
    }
}
