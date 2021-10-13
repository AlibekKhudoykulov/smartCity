package com.example.smartcity.entity;

import com.example.smartcity.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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




}
