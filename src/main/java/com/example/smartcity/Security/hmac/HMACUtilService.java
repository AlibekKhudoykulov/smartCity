package com.example.smartcity.Security.hmac;


public interface HMACUtilService {


    String calculateHASH(String keyId, String timestamp, String action, String secretKey);

    boolean hasAccess(String keyId, String timestamp, String action, String signature);
}
