package com.qingying0.aqachat.service;

import com.qingying0.aqachat.entity.Message;
import com.qingying0.aqachat.entity.UserSession;

import java.util.List;

public interface IUserSessionService {

    void saveUserSession(Long user1Id, Long user2Id);

    List<UserSession> getUserSession();

    void updateUserUnreadnumZero(Long sessionId, Long userId);

    void updateNickname(String nickname);

    void updateSessionBySendMessage(Message message);
}
