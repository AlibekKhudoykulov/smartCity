package com.example.smartcity.Repository;

import com.example.smartcity.Entity.HMACSecretKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HMACSecretKeyRepository extends JpaRepository<HMACSecretKey, Integer> {


    @Query(value = "select secret_key from hmacsecret_key where component_name=:?1", nativeQuery = true)
    String getSecretKeyByComponentName(String componentName);
}
