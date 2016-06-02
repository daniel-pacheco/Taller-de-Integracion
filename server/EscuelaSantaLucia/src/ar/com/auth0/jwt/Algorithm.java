package ar.com.auth0.jwt;

/* CREDITOS: https://github.com/auth0/java-jwt
 * AVISO: El c�digo aqu� escrito puede encontrarse modificado con respecto al c�digo que se encuentra en el 
 *        repositorio se�alado en CR�DITO. Si desea usar este c�digo o el citado, por favor, colabore y haga 
 *        menci�n al autor original.
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
