package com.example.smartcity.security.hmac;


public interface HMACUtilService {


    String calculateHASH(String keyId, String timestamp, String action, String secretKey);

    boolean hasAccess(String keyId, String timestamp, String action, String signature);
}
