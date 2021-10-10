package com.example.smartcity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class VictimDTO {

    private long cardNumber;

    private Date deathDate;

    private String remark;

    private List<UUID> crimes;

    public VictimDTO(long cardNumber, Date deathDate, String remark) {
        this.cardNumber = cardNumber;
        this.deathDate = deathDate;
        this.remark = remark;
    }
}
