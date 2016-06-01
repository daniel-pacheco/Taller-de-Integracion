package ar.com.auth0.jwt;

/* CREDITOS: https://github.com/auth0/java-jwt
 * AVISO: El código aquí escrito puede encontrarse modificado con respecto al código que se encuentra en el 
 *        repositorio señalado en CRÉDITO. Si desea usar este código o el citado, por favor, colabore y haga 
 *        mención al autor original.
*/

public class JWTExpiredException extends JWTVerifyException {
    private long expiration;

    public JWTExpiredException(long expiration) {
        this.expiration = expiration;
    }

    public JWTExpiredException(String message, long expiration) {
        super(message);
        this.expiration = expiration;
    }

    public long getExpiration() {
        return expiration;
    };
}
