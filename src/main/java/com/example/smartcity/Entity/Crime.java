package com.example.smartcity.Entity;

import com.example.smartcity.Entity.Enums.CrimeReportStatus;
import com.example.smartcity.Entity.Enums.CrimeStatus;
import com.example.smartcity.Entity.Enums.CrimeType;
import com.example.smartcity.Entity.Template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Crime extends AbsEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Date crimeTime;

    private String crimeDescription;

    @ManyToMany
    private List<Witness> witnesses;

    @ManyToMany
    private List<Victim> victims;

    @ManyToMany
    private List<Arrested> arrested;

    @ManyToMany
    private List<Officer> officers;

    @OneToOne
    private PoliceStation policeStation;

    @Enumerated(EnumType.STRING)
    private CrimeReportStatus crimeReportStatus;

    @Enumerated(EnumType.STRING)
    private CrimeStatus crimeStatus;

    @Enumerated(EnumType.STRING)
    private CrimeType crimeType;


}
