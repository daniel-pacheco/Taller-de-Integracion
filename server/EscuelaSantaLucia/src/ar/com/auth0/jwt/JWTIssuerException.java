package ar.com.auth0.jwt;

/* CREDITOS: https://github.com/auth0/java-jwt
 * AVISO: El c�digo aqu� escrito puede encontrarse modificado con respecto al c�digo que se encuentra en el 
 *        repositorio se�alado en CR�DITO. Si desea usar este c�digo o el citado, por favor, colabore y haga 
 *        menci�n al autor original.
*/

public class JWTIssuerException extends JWTVerifyException {
    private final String issuer;

    public JWTIssuerException(String issuer) {
        this.issuer = issuer;
    }

    public JWTIssuerException(String message, String issuer) {
        super(message);
        this.issuer = issuer;
    }

    public String getIssuer() {
        return issuer;
    }
}
