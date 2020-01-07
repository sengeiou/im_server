package com.qingying0.aqachat.mapper;

import com.qingying0.aqachat.entity.Message;
import com.qingying0.aqachat.entity.UserSession;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    void updateUnreadnumZeroBySessionIdAndUserId(@Param("sessionId") Long sessionId, @Param("sendId") Long userId);

    void updateNickname(@Param("targetId") Long userId,@Param("nickname") String nickname);

    void updateBySendMessage(@Param("sessionId") Long sessionId,@Param("content") String content,@Param("updateTime") Date updateTime);

    void updateUnreadNumAdd(@Param("sessionId")Long sessionId, @Param("ownerId")Long userId);
}
