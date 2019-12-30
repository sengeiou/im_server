package com.qingying0.aqachat.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Request {
    private Long id;

    private Long sendId;

    private Long targetId;

    private String content;

    private Date createTime;

    private Integer type;

    private Integer status;
}
