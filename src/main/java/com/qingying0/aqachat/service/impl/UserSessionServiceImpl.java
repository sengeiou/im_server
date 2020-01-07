package com.qingying0.aqachat.service.impl;

import com.qingying0.aqachat.component.HostHolder;
import com.qingying0.aqachat.dto.UserInfoDTO;
import com.qingying0.aqachat.entity.Message;
import com.qingying0.aqachat.entity.User;
import com.qingying0.aqachat.entity.UserSession;
import com.qingying0.aqachat.mapper.UserSessionMapper;
import com.qingying0.aqachat.service.IUserService;
import com.qingying0.aqachat.service.IUserSessionService;
import com.qingying0.aqachat.utils.IdWorker;
import com.qingying0.aqachat.utils.SystemConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserSessionServiceImpl implements IUserSessionService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserSessionMapper userSessionMapper;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private IUserService userService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUserSession(Long user1Id, Long user2Id) {
        UserSession userSession = new UserSession();
        userSession.setId(idWorker.nextId());
        userSession.setOwnerId(user1Id);
        userSession.setTargetId(user2Id);
        UserInfoDTO user = userService.getUserById(userSession.getTargetId());
        userSession.setNickname(user.getUsername());
        userSession.setStatus(SystemConst.SESSION_ON);
        userSession.setUnreadNum(0);
        userSession.setIsMuted(false);
        userSession.setSessionId(idWorker.nextId());
        userSession.setSessionType(SystemConst.SESSION_TYPE_USER);
        userSession.setContent("你们已经成为好友，现在可以开始聊天");
        userSessionMapper.insert(userSession);
        userSession.setId(idWorker.nextId());
        userSession.setOwnerId(user2Id);
        userSession.setTargetId(user1Id);
        user = userService.getUserById(userSession.getTargetId());
        userSession.setNickname(user.getUsername());
        userSessionMapper.insert(userSession);
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserSession> getUserSession() {
        return userSessionMapper.selectByOwnId(hostHolder.getUserId());
    }


    @Override
    public void updateUserUnreadnumZero(Long sessionId, Long userId) {
        userSessionMapper.updateUnreadnumZeroBySessionIdAndUserId(sessionId, userId);
    }

    @Override
    public void updateNickname(String nickname) {
        userSessionMapper.updateNickname(hostHolder.getUserId(), nickname);
    }

    @Override
    public void updateSessionBySendMessage(Message message) {
        if(message.getType().equals(new Integer(0))) {
            userSessionMapper.updateBySendMessage(message.getSessionId(), message.getContent(), message.getCreateTime());
        }
        userSessionMapper.updateUnreadNumAdd(message.getSessionId(), message.getTargetId());
    }
}
