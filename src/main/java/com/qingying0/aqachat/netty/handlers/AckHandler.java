package com.qingying0.aqachat.netty.handlers;

import com.alibaba.fastjson.JSON;
import com.qingying0.aqachat.dto.AckDTO;
import com.qingying0.aqachat.dto.MsgDTO;
import com.qingying0.aqachat.entity.Message;
import com.qingying0.aqachat.enums.AckTypeEnum;
import com.qingying0.aqachat.enums.MsgTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class AckHandler {

    public String getAckOnlineMsg() {
        MsgDTO msgDTO = new MsgDTO();
        msgDTO.setType(MsgTypeEnum.ACK.getType());
        msgDTO.setData(new AckDTO(AckTypeEnum.ONLINE_ACK.getType()));
        System.out.println("online Msg" + JSON.toJSONString(msgDTO));
        return JSON.toJSONString(msgDTO);
    }

    public String getAckHeart() {
        MsgDTO msgDTO = new MsgDTO();
        msgDTO.setType(MsgTypeEnum.ACK.getType());
        msgDTO.setData(new AckDTO(AckTypeEnum.HEART_ACK.getType()));
        return JSON.toJSONString(msgDTO);
    }
}
