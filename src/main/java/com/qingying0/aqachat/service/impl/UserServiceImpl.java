package com.qingying0.aqachat.service.impl;

import com.qingying0.aqachat.component.HostHolder;
import com.qingying0.aqachat.dao.RedisDao;
import com.qingying0.aqachat.dto.UserDTO;
import com.qingying0.aqachat.dto.UserInfoDTO;
import com.qingying0.aqachat.entity.User;
import com.qingying0.aqachat.exception.CustomCode;
import com.qingying0.aqachat.exception.CustomException;
import com.qingying0.aqachat.mapper.UserMapper;
import com.qingying0.aqachat.service.IRelationService;
import com.qingying0.aqachat.service.IUserService;
import com.qingying0.aqachat.utils.IdWorker;
import com.qingying0.aqachat.utils.MD5utils;
import com.qingying0.aqachat.utils.RedisKeyUtils;
import com.qingying0.aqachat.utils.SystemConst;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private IRelationService relationService;

    /**
     * 用户注册
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
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
        // 加salt再用md5加密
        user.setPassword(MD5utils.MD5(user.getPassword() + user.getSalt()));
        userMapper.insert(user);
    }

    /**
     * 用户登陆
     * @param phone
     * @param password
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
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
        //通过雪花算法获取一个唯一的值作为token
        String token = String.valueOf(idWorker.nextId());
        //redis保存token
        redisDao.set(RedisKeyUtils.getTokenKey(token), user.getId(), 60 * 60 * 24 * 30);
        userDTO.setToken(token);
        return userDTO;
    }

    /**
     * 根据id查找用户，返回一个用户信息对象
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserInfoDTO getUserById(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if(user == null) {
            throw new CustomException(CustomCode.SEARCH_USER_FAIL);
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(user, userInfoDTO);
        userInfoDTO.setFriend(relationService.getByUserAndFriend(hostHolder.getUserId(), user.getId()) != null);
        return userInfoDTO;
    }

    /**
     * 根据字符串查找用户，匹配手机号或者用户名
     * @param phone
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserInfoDTO getUserByPhone(String phone) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        User user = userMapper.selectByPhone(phone);
        if(user == null) {
            throw new CustomException(CustomCode.SEARCH_USER_FAIL);
        }
        if(user.getId().equals(hostHolder.getUserId())) {
            throw new CustomException(CustomCode.SEARCH_OUR_FAIL);
        }
        userInfoDTO.setFriend(relationService.getByUserAndFriend(hostHolder.getUserId(), user.getId()) != null);
        BeanUtils.copyProperties(user, userInfoDTO);
        return userInfoDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateAvatarUrl(String avatarUrl) {
        userMapper.updateAvatarUrlById(hostHolder.getUserId(), avatarUrl);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateData(String username, String description) {
        userMapper.updateUsernameAndDescription(hostHolder.getUserId(), username, description);
    }
}
