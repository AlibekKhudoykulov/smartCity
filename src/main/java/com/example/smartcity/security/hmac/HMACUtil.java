package com.example.smartcity.security.hmac;


import com.example.smartcity.repository.HMACSecretKeyRepository;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Date;

@Component
public class HMACUtil implements HMACUtilService{

    private final HMACSecretKeyRepository hmacSecretKeyRepository;

    public HMACUtil(HMACSecretKeyRepository hmacSecretKeyRepository) {
        this.hmacSecretKeyRepository = hmacSecretKeyRepository;
    }

    /**
     * Checking request
     * @param keyId from request header
     * @param timestamp from request header
     * @param action from request header
     * @param signature from request header
     * @return result of checking request (true - has access, false - has not access)
     */
    public boolean hasAccess(String keyId, String timestamp, String action, String signature) {
        long now = new Date().getTime();
        if(now>Long.parseLong(timestamp)) {
            return false;
        }
        String testSignature = calculateHASH(keyId, timestamp, action, hmacSecretKeyRepository.getSecretKeyByComponentName(keyId));
        return testSignature.equals(signature);
    }

    /**
     * To calculate HASH signature
     */
    public String calculateHASH(String keyId, String timestamp, String action, String secretKey) {
        String data = "keyId="+keyId+";timestamp="+timestamp+";action="+action;  //keyId=HOTEL;timestamp=12345465;action=get_citizen_info
        try {
            SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            return new String(Base64.getEncoder().encode(rawHmac));
        } catch (GeneralSecurityException e) {
            throw new IllegalArgumentException();
        }
    }

}
