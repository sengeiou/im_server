package com.qingying0.aqachat.web.controller;

import com.qingying0.aqachat.dto.ResultDTO;
import com.qingying0.aqachat.entity.Request;
import com.qingying0.aqachat.service.IRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private IRequestService requestService;

    @GetMapping
    public ResultDTO getRequest() {
        return ResultDTO.okOf(requestService.getRequests());
    }

    @PostMapping
    public ResultDTO saveFriendRequest(Request request) {
        requestService.saveFriendRequest(request);
        return ResultDTO.okOf();
    }

    @PutMapping
    public ResultDTO updateFriendRequest(Long requestId,Integer status) {
        return ResultDTO.okOf(requestService.updateFriendRequest(requestId, status));
    }

}
