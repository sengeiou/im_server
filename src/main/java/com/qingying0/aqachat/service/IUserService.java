package com.qingying0.aqachat.service;

import com.qingying0.aqachat.dto.UserDTO;
import com.qingying0.aqachat.entity.User;

public interface IUserService {

    void register(User user);

    UserDTO login(String phone, String password);
}
