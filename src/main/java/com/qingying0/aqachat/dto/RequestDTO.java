package com.qingying0.aqachat.dto;

import lombok.Data;

@Data
public class RequestDTO {
    Long id;
    String avatarUrl;
    String username;
    Integer status;
    Integer type;
    String content;
}
