package com.qingying0.aqachat.enums;

public enum  FriendMsgType {

    ADD_FRIEND_SUCCESS(1),
    ;

    int type;

    FriendMsgType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
