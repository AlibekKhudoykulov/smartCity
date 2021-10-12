package com.example.smartcity.entity;


import com.example.smartcity.entity.enums.OfficerRank;
import com.example.smartcity.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Officer extends AbsEntity {

    private long cardNumber;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private long certificate;

    @Enumerated(EnumType.STRING)
    private OfficerRank rank;

    private UUID photoId;

    @ManyToOne
    private PoliceStation policeStation;

}
