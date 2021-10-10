package com.example.smartcity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HMACSecretKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String componentName;

    @Column(nullable = false)
    private String secretKey;

    public HMACSecretKey(String componentName, String secretKey) {
        this.componentName = componentName;
        this.secretKey = secretKey;
    }
}
