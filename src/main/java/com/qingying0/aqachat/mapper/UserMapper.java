package com.qingying0.aqachat.mapper;

import com.qingying0.aqachat.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User selectByPhone(String phone);

    void updateAvatarUrlById(@Param("id") Long id, @Param("avatarUrl") String avatarUrl);

    void updateUsernameAndDescription(@Param("id") Long id, @Param("username") String username, @Param("description") String description);
}
