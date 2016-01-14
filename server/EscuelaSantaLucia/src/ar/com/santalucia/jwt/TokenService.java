package ar.com.santalucia.jwt;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jwt.JwtClaims;


public class TokenService {
	
	public TokenService(){
		
	}
	
	public String ObtenerToken() throws Exception{
		/* En estas líneas se genera el JWK y se instancia
		 * un objeto JwtClaims (datos, entre ellos está 
		 * el tiempo de expiración de token)
		 * */
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
		return claims.toString();
	}
}
