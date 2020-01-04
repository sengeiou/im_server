package com.qingying0.aqachat.web.controller;

import com.qingying0.aqachat.dto.ResultDTO;
import com.qingying0.aqachat.service.IUserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private IUserSessionService userSessionService;

    @GetMapping
    public ResultDTO getSession() {
        return ResultDTO.okOf(userSessionService.getUserSession());
    }

}
