package com.example.smartcity.entity;

import com.example.smartcity.entity.enums.CrimeReportStatus;
import com.example.smartcity.entity.enums.CrimeStatus;
import com.example.smartcity.entity.enums.CrimeType;
import com.example.smartcity.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE crime SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
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

    private boolean deleted = Boolean.FALSE;

    public Crime(String name,
                 String address,
                 LocalDateTime crimeTime,
                 String crimeDescription,
                 List<Officer> officers,
                 PoliceStation policeStation,
                 CrimeReportStatus crimeReportStatus,
                 CrimeStatus crimeStatus,
                 CrimeType crimeType) {
        this.name = name;
        this.address = address;
        this.crimeTime = crimeTime;
        this.crimeDescription = crimeDescription;
        this.officers = officers;
        this.policeStation = policeStation;
        this.crimeReportStatus = crimeReportStatus;
        this.crimeStatus = crimeStatus;
        this.crimeType = crimeType;
    }
}
