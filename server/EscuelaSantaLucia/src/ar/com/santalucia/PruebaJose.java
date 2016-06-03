package ar.com.santalucia;

import java.security.Key;
import java.util.Arrays;
import java.util.List;

import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;
import org.jose4j.lang.JoseException;

public class PruebaJose {

	public static void main(String[] args) throws JoseException {
//		Key key = new AesKey(ByteUtil.randomBytes(16));
//		JsonWebEncryption jwe = new JsonWebEncryption();
//		jwe.setPayload("Texto de ejemplo");
//		jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
//		jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
//		jwe.setKey(key);
//		String serializedJwe = jwe.getCompactSerialization();
//		System.out.println("Serialized Encrypted JWE: " + serializedJwe);
//		jwe = new JsonWebEncryption();
//		jwe.setKey(key);
//		jwe.setCompactSerialization(serializedJwe);
//		System.out.println("Payload: " + jwe.getPayload());
	
		// PRUEBA DESDE https://bitbucket.org/b_c/jose4j/wiki/JWT%20Examples
		
		try{
			RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
			rsaJsonWebKey.setKeyId("k1");
			JwtClaims claims = new JwtClaims();
			claims.setIssuer("Issuer");
			claims.setAudience("Audience");
			claims.setExpirationTimeMinutesInTheFuture(10);
			claims.setGeneratedJwtId();
			claims.setIssuedAtToNow();
			claims.setNotBeforeMinutesInThePast(2);
			claims.setSubject("subject");
			claims.setClaim("email", "mail@example.com");
			List<String> groups = Arrays.asList("group-one", "other-group", "group-three");
			claims.setStringListClaim("groups", groups);
			
			JsonWebSignature jws = new JsonWebSignature();
			
			jws.setPayload(claims.toJson());
			
			jws.setKey(rsaJsonWebKey.getPrivateKey());
			
			jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
			
			jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
			
			String jwt = jws.getCompactSerialization();
			
			System.out.println("JWT: " + jwt);
			
			JwtConsumer jwtConsumer = new JwtConsumerBuilder()
					.setRequireExpirationTime() // the JWT must have an expiration time
		            .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
		            .setRequireSubject() // the JWT must have a subject claim
		            .setExpectedIssuer("Issuer") // whom the JWT needs to have been issued by
		            .setExpectedAudience("Audience") // to whom the JWT is intended for
		            .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
		            .build(); // create the JwtConsumer instance
			
			JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
			System.out.println("JWT validation succeeded! " + jwtClaims);
			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
}
