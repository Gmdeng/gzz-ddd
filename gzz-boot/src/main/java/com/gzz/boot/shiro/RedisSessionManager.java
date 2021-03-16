package com.gzz.boot.shiro;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

/**
 * 重写DefaultWebSessionManager的retrieveSession方法，
 * 防止一个接口重复读取redis的session
 */
public class RedisSessionManager extends DefaultWebSessionManager {
    private final Logger logger = LogManager.getLogger();

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        logger.info("防止一个接口重复读取redis的session key {}", sessionKey);
        Serializable sessionId = getSessionId(sessionKey);
        ServletRequest request = null;
        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }
        Session session = null;
        if (request != null && sessionId != null) {
            session = (Session) request.getAttribute(sessionId.toString());
//            if(session != null) return session;
//            session = sessionDAO.readSession(sessionId);
        }
        if (session != null) {
            logger.info("===终于进来这里了");
            return session;
        }
        try {
            session = super.retrieveSession(sessionKey);
        } catch (UnknownSessionException e) {
            logger.error(sessionId + "  :" + e.getMessage());
        }
        if (request != null && sessionId != null && session != null) {
            request.setAttribute(sessionId.toString(), session);
        }
        return session;
    }
}
