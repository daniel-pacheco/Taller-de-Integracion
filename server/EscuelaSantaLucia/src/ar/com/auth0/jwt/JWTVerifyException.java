package ar.com.auth0.jwt;

/* CREDITOS: https://github.com/auth0/java-jwt
 * AVISO: El código aquí escrito puede encontrarse modificado con respecto al código que se encuentra en el 
 *        repositorio señalado en CRÉDITO. Si desea usar este código o el citado, por favor, colabore y haga 
 *        mención al autor original.
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
