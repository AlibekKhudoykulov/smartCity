package com.example.smartcity.Entity;

import com.example.smartcity.Entity.Template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
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
