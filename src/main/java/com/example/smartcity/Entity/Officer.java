package com.example.smartcity.Entity;


import com.example.smartcity.Entity.Enums.OfficerRank;
import com.example.smartcity.Entity.Template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Officer extends AbsEntity {

    private long cardNumber;

    private String firstName;

    private String LastName;

    private Date birthDate;

    @Enumerated(EnumType.STRING)
    private OfficerRank rank;

    @ManyToOne
    private PoliceStation policeStation;


}
