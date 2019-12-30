package com.qingying0.aqachat.enums;

public enum RequestStatusEnum {

    SEND(0, "已发送"),
    RECEIVED(1, "已接受"),
    SUCCESS(2, "成功"),
    FAIL(3, "失败"),
    ;


    public final Integer status;
    public final String msg;

    RequestStatusEnum(Integer status,String msg) {
        this.status = status;
        this.msg = msg;
    }
}
