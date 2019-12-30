package com.qingying0.aqachat.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserSession {
    private Long sessionId;

    private Long ownerId;

    private String participantIds;

    private String nickname;

    private Date createTime;

    private Date updateTime;

    private Integer unreadNum;

    private Integer status;

    private Boolean isMuted;
}
