package com.qingying0.aqachat.mapper;

import com.qingying0.aqachat.entity.UserSession;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserSessionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserSession record);

    UserSession selectByPrimaryKey(Long id);

    List<UserSession> selectAll();

    int updateByPrimaryKey(UserSession record);

    List<UserSession> selectByOwnId(Long userId);
}
