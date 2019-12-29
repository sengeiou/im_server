package com.qingying0.aqachat.service.impl;

import com.qingying0.aqachat.dao.RedisDao;
import com.qingying0.aqachat.dto.UserDTO;
import com.qingying0.aqachat.entity.User;
import com.qingying0.aqachat.exception.CustomCode;
import com.qingying0.aqachat.exception.CustomException;
import com.qingying0.aqachat.mapper.UserMapper;
import com.qingying0.aqachat.service.IUserService;
import com.qingying0.aqachat.utils.IdWorker;
import com.qingying0.aqachat.utils.MD5utils;
import com.qingying0.aqachat.utils.RedisKeyUtils;
import com.qingying0.aqachat.utils.SystemConst;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisDao redisDao;

    @Override
    public void register(User user) {
        if(StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getPhone())) {
            throw new CustomException(CustomCode.USERNAME_OR_PASSWORD_OR_PHONE_NULL);
        }
        if(userMapper.selectByPhone(user.getPhone()) != null) {
            throw new CustomException(CustomCode.PHONE_EXIST);
        }
        user.setId(idWorker.nextId());
        user.setSalt(UUID.randomUUID().toString().substring(0, 6));
        user.setStatus(SystemConst.USER_EXIST);
        user.setPassword(MD5utils.MD5(user.getPassword() + user.getSalt()));
        userMapper.insert(user);
    }

    @Override
    public UserDTO login(String phone, String password) {
        User user = userMapper.selectByPhone(phone);
        if(user == null) {
            throw new CustomException(CustomCode.PHONE_NOT_EXIST);
        }
        if(!user.getPassword().equals(MD5utils.MD5(password + user.getSalt()))) {
            throw new CustomException(CustomCode.PASSWORD_FAIL);
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        String token = UUID.randomUUID().toString().substring(0, 8);
        redisDao.set(RedisKeyUtils.getTokenKey(user.getId()), token);
        userDTO.setToken(token);
        return userDTO;
    }
}
