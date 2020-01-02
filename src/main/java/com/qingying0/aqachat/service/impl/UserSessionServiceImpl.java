package com.qingying0.aqachat.service.impl;

import com.qingying0.aqachat.entity.UserSession;
import com.qingying0.aqachat.mapper.UserSessionMapper;
import com.qingying0.aqachat.service.IUserSessionService;
import com.qingying0.aqachat.utils.IdWorker;
import com.qingying0.aqachat.utils.SystemConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserSessionServiceImpl implements IUserSessionService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserSessionMapper userSessionMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUserSession(Long user1Id, Long user2Id) {
        UserSession userSession = new UserSession();
        userSession.setId(idWorker.nextId());
        userSession.setOwnerId(user1Id);
        userSession.setTargetId(user2Id);
        userSession.setStatus(SystemConst.SESSION_ON);
        userSession.setUnreadNum(0);
        userSession.setIsMuted(false);
        userSession.setSessionId(idWorker.nextId());
        userSessionMapper.insert(userSession);
        userSession.setId(idWorker.nextId());
        userSession.setOwnerId(user2Id);
        userSession.setTargetId(user1Id);
        userSessionMapper.insert(userSession);
    }

}
