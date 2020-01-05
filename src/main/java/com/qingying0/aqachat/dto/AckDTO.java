package com.qingying0.aqachat.dto;

import lombok.Data;

@Data
public class AckDTO {
    int type;

    public AckDTO(int type) {
        this.type = type;
    }
}
