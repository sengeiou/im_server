package com.qingying0.aqachat.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserSession {
    private Long id;

    private Long ownerId;

    private Long targetId;

    private Long sessionId;

    private String nickname;

    private Date createTime;

    private Date updateTime;

    private Integer unreadNum;

    private Integer status;

    private Boolean isMuted;

    private String content;

    private Integer sessionType;
}
