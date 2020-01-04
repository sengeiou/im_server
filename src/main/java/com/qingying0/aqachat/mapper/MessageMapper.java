package com.qingying0.aqachat.mapper;

import com.qingying0.aqachat.entity.Message;
import java.util.List;

public interface MessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Message record);

    Message selectByPrimaryKey(Long id);

    List<Message> selectAll();

    int updateByPrimaryKey(Message record);
}