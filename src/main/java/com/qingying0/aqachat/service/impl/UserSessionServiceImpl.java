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
        userSession.setSessionId(idWorker.nextId());
        userSession.setOwnerId(user1Id);
        String participantIds = user1Id < user2Id ? user1Id + "_" + user2Id : user2Id + "_" + user1Id;
        userSession.setParticipantIds(participantIds);
        userSession.setStatus(SystemConst.SESSION_OFF);
        userSessionMapper.insert(userSession);
        userSession.setOwnerId(user2Id);
        userSessionMapper.insert(userSession);
    }

}
