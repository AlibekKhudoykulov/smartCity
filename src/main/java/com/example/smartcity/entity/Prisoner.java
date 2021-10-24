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
@SQLDelete(sql = "UPDATE prisoner SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Prisoner extends AbsEntity {

    private Long cardNumber;

    private String firstName;

    private String surname;

    private Date birthDate;

    private String prisonDuration;

    private Date startingDate;

    private Date endingDate;

    private boolean inPrison;

    private UUID photoId;

    @ManyToMany
    private List<Crime> crime;

    private boolean deleted = Boolean.FALSE;

    public Prisoner(Long cardNumber,
                    String firstName,
                    String surname,
                    Date birthDate,
                    String prisonDuration,
                    Date startingDate,
                    Date endingDate,
                    boolean inPrison,
                    UUID photoId,
                    List<Crime> crime) {
        this.cardNumber = cardNumber;
        this.firstName = firstName;
        this.surname = surname;
        this.birthDate = birthDate;
        this.prisonDuration = prisonDuration;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.inPrison = inPrison;
        this.photoId = photoId;
        this.crime = crime;
    }
}
