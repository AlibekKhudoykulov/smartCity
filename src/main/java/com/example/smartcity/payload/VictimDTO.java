package com.example.smartcity.payload;

import lombok.Data;

@Data
public class VictimDTO {

    private long cardNumber;

    private String phoneNumber;

    private String remark;

    public VictimDTO(long cardNumber) {
        this.cardNumber = cardNumber;
    }
}
