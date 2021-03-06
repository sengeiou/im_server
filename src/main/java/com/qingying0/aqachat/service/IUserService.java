package com.qingying0.aqachat.service;

import com.qingying0.aqachat.dto.UserDTO;
import com.qingying0.aqachat.dto.UserInfoDTO;
import com.qingying0.aqachat.entity.User;

public interface IUserService {

    void register(User user);

    UserDTO login(String phone, String password);

    UserInfoDTO getUserById(Long id);

    UserInfoDTO getUserByPhone(String phone);

    void updateAvatarUrl(String avatarUrl);

    void updateData(String username, String description);
}
