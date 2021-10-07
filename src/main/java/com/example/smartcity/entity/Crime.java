package com.example.smartcity.entity;

import com.example.smartcity.entity.enums.CrimeReportStatus;
import com.example.smartcity.entity.enums.CrimeStatus;
import com.example.smartcity.entity.enums.CrimeType;
import com.example.smartcity.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Crime extends AbsEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDateTime crimeTime;

    private String crimeDescription;

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