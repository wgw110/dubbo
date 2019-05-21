package tv.mixiong.ds.cache.im;

import mobi.mixiong.cache.BaseValueCache;
import org.springframework.stereotype.Repository;
import tv.mixiong.ds.service.im.co.ImUserSignCo;


/**
 * ImUserSignCache
 */
@Repository("imUserSignCache")
public class ImUserSignCache extends BaseValueCache<String, ImUserSignCo> {
    @Override
    protected String getPrefix() {
        return imUserSignCoKey;
    }

    @Override
    protected Long getTTL() {
        return TTL;
    }

    private String imUserSignCoKey = "mx:api:live:im:user_sign:";

    private long TTL = 86400L;
}
