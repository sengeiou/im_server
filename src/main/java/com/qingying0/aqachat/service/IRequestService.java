package com.qingying0.aqachat.service;

import com.qingying0.aqachat.dto.UserInfoDTO;
import com.qingying0.aqachat.entity.Request;

public interface IRequestService {

    void saveFriendRequest(Request request);

    void updateFriendRequest(Long requestId, Integer status);
}

