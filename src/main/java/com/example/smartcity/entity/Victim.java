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
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE victim SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Victim extends AbsEntity {

    private Long cardNumber;

    private String name;

    private String surname;

    private Date birthDate;

    private Date deathDate;

    private String remark;

    private UUID photoId;

    private boolean isDead;

    @ManyToMany
    private List<Crime> crime;

    private boolean deleted = Boolean.FALSE;

    public Victim(Long cardNumber,
                  String name,
                  String surname,
                  Date birthDate,
                  Date deathDate,
                  String remark,
                  UUID photoId,
                  boolean isDead,
                  List<Crime> crime) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.remark = remark;
        this.photoId = photoId;
        this.isDead = isDead;
        this.crime = crime;
    }
}
