package com.qingying0.aqachat.service;

import com.qingying0.aqachat.dto.RequestDTO;
import com.qingying0.aqachat.dto.UserInfoDTO;
import com.qingying0.aqachat.entity.Request;

import java.util.List;

public interface IRequestService {

    void saveFriendRequest(Request request);

    Request updateFriendRequest(Long requestId, Integer status);

    List<RequestDTO> getRequests();
}

