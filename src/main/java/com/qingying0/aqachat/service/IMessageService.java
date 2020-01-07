package com.qingying0.aqachat.service;

import com.qingying0.aqachat.entity.Message;

import java.util.List;

public interface IMessageService {

    void saveMessage(Message message);

    void deleteMessageById(Long id);

    List<Message> getByTargetId(Long targetId);
}
