package com.qingying0.aqachat.enums;

public enum AckTypeEnum {

    ONLINE_ACK(0),
    HEART_ACK(1),

    ;
    Integer type;

    public Integer getType() {
        return type;
    }

    AckTypeEnum(Integer type) {
        this.type = type;
    }

}
