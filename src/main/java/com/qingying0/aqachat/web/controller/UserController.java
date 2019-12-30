package com.qingying0.aqachat.web.controller;

import com.qingying0.aqachat.dto.ResultDTO;
import com.qingying0.aqachat.dto.UserInfoDTO;
import com.qingying0.aqachat.entity.Request;
import com.qingying0.aqachat.service.IRequestService;
import com.qingying0.aqachat.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRequestService requestService;

    @GetMapping("/search_by_phone")
    public ResultDTO getUserByPhone(String phone) {
        return ResultDTO.okOf(userService.getUserByPhone(phone));
    }

    @GetMapping("/{userId}")
    public ResultDTO getUser(@PathParam("userId") Long userId) {
        return ResultDTO.okOf(userService.getUserById(userId));
    }

    @PostMapping("/friend_request")
    public ResultDTO saveFriendRequest(Request request) {
        requestService.saveFriendRequest(request);
        return ResultDTO.okOf();
    }

    @PostMapping("/received_friend_request")
    public ResultDTO updateFriendRequest(Long requestId,Integer status) {
        requestService.updateFriendRequest(requestId, status);
        return ResultDTO.okOf();
    }

}
