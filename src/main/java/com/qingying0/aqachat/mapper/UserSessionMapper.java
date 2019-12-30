package com.qingying0.aqachat.mapper;

import com.qingying0.aqachat.entity.UserSession;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserSessionMapper {
    int deleteByPrimaryKey(Long sessionId);

    int insert(UserSession record);

    UserSession selectByPrimaryKey(Long sessionId);

    List<UserSession> selectAll();

    int updateByPrimaryKey(UserSession record);
}
