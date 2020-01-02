package com.qingying0.aqachat.exception;

public enum CustomCode implements ICustomCode {

    SUCCESS(200, "成功"),
    FAIL(2000, "出现未知错误"),
    PHONE_EXIST(2001, "该手机号已注册"),
    MD5_FAIL(2002, "MD5转化失败"),
    PHONE_NOT_EXIST(2003, "手机号未注册"),
    PASSWORD_FAIL(2004, "密码错误"),
    USERNAME_OR_PASSWORD_OR_PHONE_NULL(2005, "选项不能为空"),
    SEARCH_USER_FAIL(2006, "未找到到用户"),
    SEARCH_OUR_FAIL(2007, "不能添加自己为好友"),
    FRIEND_RELATION_EXIST(2008, "已经添加为好友"),
    FAIL_REQUEST(2009, "不合法的请求"),
    EXIST_FRIEND_REQUEST(2010, "已经发送好友请求,不能重复发送"),
    REFUSE_REQUEST(2011, "好友请求被拒绝"),
    ;

    private String message;
    private Integer code;

    CustomCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
