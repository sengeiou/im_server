package com.qingying0.aqachat.enums;

import lombok.Data;

public enum MsgTypeEnum {
    HEART(0),
    ONLINE(1),
    SENDMESSAGE(2),
    FRIEND(3),
    SESSION(4),
    REQUEST(5),
    ;

    int type;

    MsgTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public MsgTypeEnum getByType(int type) {
        for(MsgTypeEnum msg : values()) {
            if(msg.getType() == type) {
                return msg;
            }
        }
        return null;
    }
}
