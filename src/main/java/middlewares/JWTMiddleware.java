package middlewares;

import util.SecretsConfig;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

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
			jwt = JWT.create().withHeader(headerMap).withClaim("userId", userId).withIssuedAt(Instant.now())
					.withExpiresAt(Instant.now().plusSeconds(3600)).sign(algorithm);

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

	public int verifyToken(String token) {
		int userId = 0;

		try {
			String secret = SecretsConfig.getJWTVerifySecretKey();

			// Define the signing algorithm
			Algorithm algorithm = Algorithm.HMAC256(secret);

			// Verify jwt token
			JWTVerifier verifier = JWT.require(algorithm).build();

			// Decode the jwt token
			DecodedJWT jwt = verifier.verify(token);

			// Assign user id from the decoded token
			userId = jwt.getClaim("userId").asInt();

		} catch (JWTVerificationException e) {
			System.out.println("Error Validating JWT Token.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("JWT Verification Generalised Error.");
			e.printStackTrace();
		}
		return userId;
	}
}
