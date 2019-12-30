package com.qingying0.aqachat.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Relation {
    private Long id;

    private Long userId;

    private Long friendId;

    private Date createTime;

    private Integer status;

}
