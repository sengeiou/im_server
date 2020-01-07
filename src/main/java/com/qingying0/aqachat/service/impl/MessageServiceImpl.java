package com.qingying0.aqachat.service.impl;

import com.qingying0.aqachat.entity.Message;
import com.qingying0.aqachat.mapper.MessageMapper;
import com.qingying0.aqachat.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void saveMessage(Message message) {
        messageMapper.insert(message);
    }

    @Override
    public void deleteMessageById(Long id) {
        messageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Message> getByTargetId(Long targetId) {
        return messageMapper.selectByTargetId(targetId);
    }
}
