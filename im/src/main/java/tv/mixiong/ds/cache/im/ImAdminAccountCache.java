package tv.mixiong.ds.cache.im;

import mobi.mixiong.cache.BaseValueCache;
import org.springframework.stereotype.Repository;
import tv.mixiong.ds.dto.im.ImAdminAccountDto;

/**
 * ImUserSignCache
 */
@Repository("imAdminAccountCache")
public class ImAdminAccountCache extends BaseValueCache<Integer, ImAdminAccountDto> {
    @Override
    protected String getPrefix() {
        return imUserSignCoKey;
    }

    @Override
    protected Long getTTL() {
        return TTL;
    }

    private String imUserSignCoKey = "mx:api:live:im:admin_account:";

    private long TTL = 86400L;
}
