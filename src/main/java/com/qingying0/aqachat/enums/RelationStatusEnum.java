package com.qingying0.aqachat.enums;

public enum  RelationStatusEnum {

    FRIEND(0, "好友"),
    DELETE(1, "删除好友"),
    SHIELDING(2, "屏蔽"),
    ;

    public final Integer status;
    public final String msg;

    RelationStatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

}
