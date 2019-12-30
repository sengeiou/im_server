package com.qingying0.aqachat.dto;

import lombok.Data;

@Data
public class MessageDTO {

    private Integer type;
    private String data;
    private Long sendId;
    private Long targetId;

}
