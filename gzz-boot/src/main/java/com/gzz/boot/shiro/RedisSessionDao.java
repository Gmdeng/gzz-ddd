package com.gzz.boot.shiro;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 继承AbstractSessionDAO，实现Redis Session的增刪改查操作
 */
public class RedisSessionDao extends AbstractSessionDAO {
    private final Logger logger = LogManager.getLogger();
    private static final String SESSION_PREFIX = "shiro:session:";
    private RedisTemplate<String, Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    /**
     * 更新SESSION
     *
     * @param session
     * @throws UnknownSessionException
     */
    public void update(Session session) throws UnknownSessionException {
        logger.debug("更新session id:{}", session.getId().toString());
        saveSession(session);
    }

    /**
     * 删除SESSION
     *
     * @param session
     */
    public void delete(Session session) {
        logger.debug("删除session id:{}", session.getId().toString());
        redisTemplate.delete(getKey(session.getId()));
    }

    /**
     * 获在线SESSION
     */
    public Collection<Session> getActiveSessions() {
        logger.debug("获取在线SESSION");
        Set<String> keys = keys();
        Set<Session> sessions = new HashSet<Session>();
        if (keys.size() == 0) return sessions;
        for (String id : keys) {
            Session _session = getSession(id);
            if (_session == null) continue;
            sessions.add(_session);
        }
        return sessions;
    }

    /**
     * 创建SESSION
     *
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable id = generateSessionId(session);
        logger.debug("创建session id:{}", id.toString());
        assignSessionId(session, id);//将session 和 sessionId捆绑在一起
        saveSession(session);
        return id;
    }


    /**
     * 读取Session
     *
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) return null;
        logger.debug("读取session id:{}", sessionId.toString());
        return getSession(sessionId);
    }

    private Session getSession(Serializable id) {
        try {
            Object obj = redisTemplate.boundValueOps(getKey(id)).get();
            // Object obj = redisTemplate.opsForValue().get(getKey(id));
            return (Session) obj;
        }catch (Exception e) {
            logger.error("获取失败"+ e.getMessage());
        }
        return null;
    }

    private void saveSession(Session session) {
        if (session != null && session.getId() != null) {
            Serializable id = session.getId();
            redisTemplate.opsForValue().set(getKey(id), session, 30, TimeUnit.MINUTES);
        }
    }

    private static String getKey(Serializable id) {
        return SESSION_PREFIX + id.toString();
    }

    private Set<String> keys() {
        return redisTemplate.execute(new RedisCallback<Set<String>>() {
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                Set<String> binaryKeys = new HashSet<String>();
                Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(SESSION_PREFIX + "*").count(1000).build());
                while (cursor.hasNext()) {
                    binaryKeys.add(new String(cursor.next()));
                }
                connection.close();
                return binaryKeys;
            }
        });
    }
}
