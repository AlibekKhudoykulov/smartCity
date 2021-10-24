package com.example.smartcity.entity;

import com.example.smartcity.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE police_station SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class PoliceStation extends AbsEntity {

    private String name;

    private String address;

    private String phoneNumber;

    private String remark;

    private boolean deleted = Boolean.FALSE;

    public PoliceStation(String name,
                         String address,
                         String phoneNumber,
                         String remark) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.remark = remark;
    }
}
