package com.qingying0.aqachat.web.controller;

import com.qingying0.aqachat.dto.ResultDTO;
import com.qingying0.aqachat.service.IRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
