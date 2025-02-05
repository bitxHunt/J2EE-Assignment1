package middlewares;

import util.SecretsConfig;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

public class JWTMiddleware {
	
	public String createVerifyToken(int userId) {
		String jwt = "";
		
		try {
			String secret = SecretsConfig.getJWTVerifySecretKey();

			// Define the signing algorithm
			Algorithm algorithm = Algorithm.HMAC256(secret);

			// Define the header of the token
			Map<String, Object> headerMap = new HashMap<>();
			headerMap.put("alg", SecretsConfig.getJWTAlgorithm());
			headerMap.put("typ", "JWT");

			// Set Payload, Expiration and generate the token
			jwt = JWT.create().withHeader(headerMap)
					.withClaim("userId", userId)
					.withIssuedAt(Instant.now())
					.withExpiresAt(Instant.now().plusSeconds(3600))
					.sign(algorithm);

			System.out.println("Generated JWT Token: " + jwt);
			
		} catch (JWTCreationException e) {
			System.out.println("Error Generating JWT Token.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("JWT Creation Generalised Error.");
			e.printStackTrace();
		}
		return jwt;
	}
}
