package ar.com.auth0.jwt;

/* CREDITOS: https://github.com/auth0/java-jwt
 * AVISO: El c�digo aqu� escrito puede encontrarse modificado con respecto al c�digo que se encuentra en el 
 *        repositorio se�alado en CR�DITO. Si desea usar este c�digo o el citado, por favor, colabore y haga 
 *        menci�n al autor original.
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
