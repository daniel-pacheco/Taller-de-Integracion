package ar.com.auth0.jwt;

/* CREDITOS: https://github.com/auth0/java-jwt
 * AVISO: El c�digo aqu� escrito puede encontrarse modificado con respecto al c�digo que se encuentra en el 
 *        repositorio se�alado en CR�DITO. Si desea usar este c�digo o el citado, por favor, colabore y haga 
 *        menci�n al autor original.
*/

public class JWTVerifyException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4911506451239107610L;

	public JWTVerifyException() {
    }

    public JWTVerifyException(String message, Throwable cause) {
		super(message, cause);
	}


	public JWTVerifyException(String message) {
        super(message);
    }
}
