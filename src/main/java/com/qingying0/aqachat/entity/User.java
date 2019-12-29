package com.qingying0.aqachat.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;

    private String username;

    private String password;

    private String phone;

    private Date createTime;

    private Date updateTime;

    private String description;

    private String qrcode;

    private Integer status;

    private String avatorUrl;

    private String salt;

    private String pushId;
}
