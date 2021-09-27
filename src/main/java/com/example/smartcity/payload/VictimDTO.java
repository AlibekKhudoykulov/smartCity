package com.example.smartcity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class VictimDTO {

    private long cardNumber;

    private String phoneNumber;

    private String remark;

    private List<UUID> crimes;
}
