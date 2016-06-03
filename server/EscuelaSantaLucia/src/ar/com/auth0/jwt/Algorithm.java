package ar.com.auth0.jwt;

/* CREDITOS: https://github.com/auth0/java-jwt
 * AVISO: El código aquí escrito puede encontrarse modificado con respecto al código que se encuentra en el 
 *        repositorio señalado en CRÉDITO. Si desea usar este código o el citado, por favor, colabore y haga 
 *        mención al autor original.
*/

public enum Algorithm {
	HS256("HmacSHA256"), HS384("HmacSHA384"), HS512("HmacSHA512"), RS256("RS256"), RS384("RS384"), RS512("RS512");

	private Algorithm(String value) {
		this.value = value;
	}
	
	private String value;

	public String getValue() {
		return value;
	}
}
