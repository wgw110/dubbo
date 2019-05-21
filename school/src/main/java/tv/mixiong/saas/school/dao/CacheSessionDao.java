package tv.mixiong.saas.school.dao;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tv.mixiong.saas.school.cache.SessionCache;
import tv.mixiong.saas.school.cache.UserSessionCache;
import tv.mixiong.saas.school.entity.SessionVo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CacheSessionDao extends CachingSessionDAO {
    @Autowired
    private SessionCache sessionCache;

    @Autowired
    private UserSessionCache userSessionCache;

    @Override
    protected void doUpdate(Session session) {
        if (session == null) {
            return;
        }
        sessionCache.set(session.getId().toString(), SessionVo.from(SerializationUtils.serialize((Serializable)
                session)));
        if (session.getAttributeKeys().contains("user_name")) {
            String username = String.valueOf(session.getAttribute("user_name"));
            userSessionCache.put(username, session.getId().toString(), session.getId().toString());
        }
    }

    @Override
    protected void doDelete(Session session) {
        sessionCache.drop(session.getId().toString());
        if (session.getAttributeKeys().contains("user_name")) {
            String username = String.valueOf(session.getAttribute("user_name"));
            userSessionCache.delete(username, session.getId().toString());
        }
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable serializable = this.generateSessionId(session);
        this.assignSessionId(session, serializable);
        return serializable;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        String key = serializable.toString();
        SessionVo s = sessionCache.get(key);
        if (s != null) {
            return SerializationUtils.deserialize(s.getData());
        } else {
            return null;
        }
    }

    public void kickout(String userName) {
        Map<String, ?> map = userSessionCache.entries(userName);
        if (map != null && map.size() > 0) {
            List<String> sessionIds = map.keySet().stream().collect(Collectors.toList());
            sessionCache.mutDrop(sessionIds);
            userSessionCache.drop(userName);
        }
    }
}
