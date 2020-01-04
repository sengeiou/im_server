package com.qingying0.aqachat.web.controller;

import com.qingying0.aqachat.dto.FriendDTO;
import com.qingying0.aqachat.dto.FriendMsgDTO;
import com.qingying0.aqachat.dto.MsgDTO;
import com.qingying0.aqachat.dto.ResultDTO;
import com.qingying0.aqachat.enums.FriendMsgType;
import com.qingying0.aqachat.enums.MsgTypeEnum;
import com.qingying0.aqachat.service.IRelationService;
import com.qingying0.aqachat.service.impl.RelationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class RelationController {

    @Autowired
    private IRelationService relationService;

    @GetMapping
    public ResultDTO getFriends() {
        return ResultDTO.okOf(relationService.getFriends());
    }

}
