package com.example.smartcity.entity;


import com.example.smartcity.entity.enums.OfficerRank;
import com.example.smartcity.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE officer SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
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

    private boolean deleted = Boolean.FALSE;

    public Officer(long cardNumber,
                   String firstName,
                   String lastName,
                   Date birthDate,
                   long certificate,
                   OfficerRank rank,
                   UUID photoId,
                   PoliceStation policeStation) {
        this.cardNumber = cardNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.certificate = certificate;
        this.rank = rank;
        this.photoId = photoId;
        this.policeStation = policeStation;
    }
}
