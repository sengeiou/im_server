package com.qingying0.aqachat.enums;

public enum FriendMsgTypeEnum {

    ADD_FRIEND_SUCCESS(0),
    ;

    int type;

    FriendMsgTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
