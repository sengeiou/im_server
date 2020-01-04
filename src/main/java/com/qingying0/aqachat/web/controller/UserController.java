package com.qingying0.aqachat.web.controller;

import com.qingying0.aqachat.dto.ResultDTO;
import com.qingying0.aqachat.dto.UserInfoDTO;
import com.qingying0.aqachat.entity.Request;
import com.qingying0.aqachat.service.IRequestService;
import com.qingying0.aqachat.service.IUserService;
import com.qingying0.aqachat.utils.QiNiuUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/search_by_phone")
    public ResultDTO getUserByPhone(String phone) {
        return ResultDTO.okOf(userService.getUserByPhone(phone));
    }

    @GetMapping("/{userId}")
    public ResultDTO getUser(@PathParam("userId") Long userId) {
        return ResultDTO.okOf(userService.getUserById(userId));
    }

    @PostMapping("/upload")
    public ResultDTO uploadFile(@RequestParam("file")MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                System.out.println("originalfilename = " + file.getOriginalFilename());
                System.out.println("name = " + file.getName());
            }
            String path = QiNiuUpload.upload(file.getBytes(), file.getOriginalFilename());

            return ResultDTO.okOf(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultDTO.errorOf(2000, "上传错误");
    }

    @PostMapping("/updateAvatar")
    public ResultDTO updateAvatar(String avatarUrl) {
        userService.updateAvatarUrl(avatarUrl);
        return ResultDTO.okOf();
    }

    @PostMapping("/updateData")
    public ResultDTO updateUserData(String username, String description) {
        userService.updateData(username, description);
        return ResultDTO.okOf();
    }

}
