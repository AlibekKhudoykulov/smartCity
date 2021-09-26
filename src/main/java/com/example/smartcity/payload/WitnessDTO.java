package com.example.smartcity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WitnessDTO {

    private long cardNumber;

    private String phoneNumber;

    private String remark;

    public WitnessDTO(long cardNumber) {
        this.cardNumber = cardNumber;
    }
}
