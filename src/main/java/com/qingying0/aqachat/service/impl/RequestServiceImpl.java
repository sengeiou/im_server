package com.qingying0.aqachat.service.impl;

import com.qingying0.aqachat.component.HostHolder;
import com.qingying0.aqachat.dao.RedisDao;
import com.qingying0.aqachat.entity.Relation;
import com.qingying0.aqachat.entity.Request;
import com.qingying0.aqachat.enums.RequestStatusEnum;
import com.qingying0.aqachat.exception.CustomCode;
import com.qingying0.aqachat.exception.CustomException;
import com.qingying0.aqachat.mapper.RequestMapper;
import com.qingying0.aqachat.service.IRelationService;
import com.qingying0.aqachat.service.IRequestService;
import com.qingying0.aqachat.service.IUserSessionService;
import com.qingying0.aqachat.utils.IdWorker;
import com.qingying0.aqachat.utils.RedisKeyUtils;
import com.qingying0.aqachat.utils.SystemConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 请求表
 */
@Service
public class RequestServiceImpl implements IRequestService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private IRelationService relationService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private IUserSessionService userSessionService;

    /**
     * 保存好友请求，设置请求状态为已发送
     * @param request
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveFriendRequest(Request request) {
        request.setSendId(hostHolder.getUserId());
        if(request.getSendId().equals(request.getTargetId())) {
            throw new CustomException(CustomCode.SEARCH_OUR_FAIL);
        }
        //查看是否已经为好友
        Relation relation = relationService.getByUserAndFriend(request.getSendId(), request.getTargetId());
        if(relation != null && relation.getStatus() == 1) {
            throw new CustomException(CustomCode.FRIEND_RELATION_EXIST);
        }
        request.setId(idWorker.nextId());
        request.setType(SystemConst.REQUEST_FRIEND);
        request.setStatus(RequestStatusEnum.SEND.status);
        requestMapper.insert(request);
        redisDao.lSetLeft(RedisKeyUtils.getTargetRequestKey(request.getTargetId()), request);
    }

    @Override
    public void updateFriendRequest(Long requestId, Integer status) {
        Request request = requestMapper.selectByPrimaryKey(requestId);
        if(request == null) {
            throw new CustomException(CustomCode.FAIL);
        }
        if(status.equals(RequestStatusEnum.RECEIVED.status)) {

        }
        if(status.equals(RequestStatusEnum.SUCCESS.status)) {
            relationService.saveRelation(request.getSendId(), request.getTargetId());
            userSessionService.saveUserSession(request.getSendId(), request.getTargetId());
            request.setStatus(status);
            requestMapper.updateByPrimaryKey(request);

        }
        if(status.equals(RequestStatusEnum.FAIL.status)) {
            request.setStatus(status);
            requestMapper.updateByPrimaryKey(request);
            return;
        }
    }
}
