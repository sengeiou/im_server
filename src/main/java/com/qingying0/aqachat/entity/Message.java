package com.qingying0.aqachat.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Long id;

    private String content;

    private Date createTime;

    private Long sendId;

    private Long targetId;

    private Integer type;

    private Integer status;

    private Long sessionId;
}
