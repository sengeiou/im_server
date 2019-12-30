package com.qingying0.aqachat.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String username;

    private String password;

    private String phone;

    private String token;

    private String avatarUrl;

    private String description;
}
