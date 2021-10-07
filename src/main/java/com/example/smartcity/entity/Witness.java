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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Witness extends AbsEntity {

    private Long cardNumber;

    private String firstName;

    private String surname;

    private Date birthDate;

    private String phoneNumber;

    private String remark;

    @ManyToMany
    private List<Crime> crime;

}
