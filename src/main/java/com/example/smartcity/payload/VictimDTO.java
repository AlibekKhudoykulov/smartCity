package com.example.smartcity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VictimDTO {

    @NotNull(message = "Card number must not be empty")
    private long cardNumber;

    private Date deathDate;

    private String remark;

    private boolean isDead;

    private List<UUID> crimes;


}
