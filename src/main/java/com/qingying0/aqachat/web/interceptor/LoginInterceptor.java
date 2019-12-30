package com.qingying0.aqachat.web.interceptor;

import com.qingying0.aqachat.component.HostHolder;
import com.qingying0.aqachat.dao.RedisDao;
import com.qingying0.aqachat.entity.User;
import com.qingying0.aqachat.utils.JwtUtils;
import com.qingying0.aqachat.utils.RedisKeyUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private RedisDao redisDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        log.info("token" + request.getHeader("token"));
        Long userId = (Long)redisDao.get(RedisKeyUtils.getTokenKey(token));
        hostHolder.setUserId(userId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.delete();
    }
}
