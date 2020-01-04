package com.qingying0.aqachat.dto;


import lombok.Data;

import java.util.Date;

@Data
public class FriendDTO {
    private Long id;
    private String username;
    private String avatarUrl;
    private Date createTime;
    private String description;
    private Boolean sex;
    private Integer status;
}
