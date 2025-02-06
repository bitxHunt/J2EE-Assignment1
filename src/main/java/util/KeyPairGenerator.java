package util;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyPairGenerator {
	private static final SecureRandom secureRandom = new SecureRandom();

	private static String generateKey(int length) {
		byte[] randomBytes = new byte[length];
		secureRandom.nextBytes(randomBytes);
		String key = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
		return key.substring(0, length); 
	}

	public static String generateAccessKey() {
		return generateKey(15); // Generates exactly 15 characters
	}

	public static String generateSecretKey() {
		return generateKey(20); // Generates exactly 20 characters
	}
}