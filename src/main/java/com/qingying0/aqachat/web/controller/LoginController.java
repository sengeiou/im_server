package com.qingying0.aqachat.web.controller;

import com.qingying0.aqachat.dto.ResultDTO;
import com.qingying0.aqachat.dto.UserDTO;
import com.qingying0.aqachat.entity.User;
import com.qingying0.aqachat.exception.CustomException;
import com.qingying0.aqachat.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResultDTO login(String phone, String password) {
        System.out.println(phone + password);
        UserDTO userDTO = userService.login(phone, password);
        return ResultDTO.okOf(userDTO);
    }

    @PostMapping("/register")
    public ResultDTO login(User user) {
        userService.register(user);
        return ResultDTO.okOf();
    }
}
