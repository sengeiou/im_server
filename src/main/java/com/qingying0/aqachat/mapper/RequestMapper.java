package com.qingying0.aqachat.mapper;

import com.qingying0.aqachat.dto.RequestDTO;
import com.qingying0.aqachat.entity.Request;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RequestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Request record);

    Request selectByPrimaryKey(Long id);

    List<Request> selectAll();

    int updateByPrimaryKey(Request record);

    List<RequestDTO> selectByTargetId(Long targetId);

    Request selectBySendIdAndTargetId(@Param("sendId") Long sendId, @Param("targetId") Long targetId);
}
