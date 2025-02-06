package util;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyPairGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    
    private static String generateKey(int length) {
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
    
    public static String generateAccessKey() {
        return generateKey(15);  
    }
    
    public static String generateSecretKey() {
        return generateKey(32);  
    }
}