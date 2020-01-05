package com.qingying0.aqachat.service.impl;

import com.qingying0.aqachat.entity.Message;
import com.qingying0.aqachat.mapper.MessageMapper;
import com.qingying0.aqachat.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void saveMessage(Message message) {
        messageMapper.insert(message);
    }
}
