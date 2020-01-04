package com.qingying0.aqachat.mapper;

import com.qingying0.aqachat.dto.FriendDTO;
import com.qingying0.aqachat.entity.Relation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Relation record);

    Relation selectByPrimaryKey(Long id);

    List<Relation> selectAll();

    int updateByPrimaryKey(Relation record);

    Relation selectByUserAndFriend(@Param("userId") Long userId, @Param("friendId") Long friendId);

    List<FriendDTO> selectByUserId(Long userId);
}
