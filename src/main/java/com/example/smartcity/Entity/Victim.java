package com.example.smartcity.Entity;

import com.example.smartcity.Entity.Template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Victim extends AbsEntity {

    private Long cardNumber;

    private String name;

    private String surname;

    private int age;

    private String phoneNumber;

    private String remark;


}
