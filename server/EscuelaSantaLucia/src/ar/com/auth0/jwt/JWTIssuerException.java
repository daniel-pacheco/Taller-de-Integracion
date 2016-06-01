package ar.com.auth0.jwt;

/* CREDITOS: https://github.com/auth0/java-jwt
 * AVISO: El código aquí escrito puede encontrarse modificado con respecto al código que se encuentra en el 
 *        repositorio señalado en CRÉDITO. Si desea usar este código o el citado, por favor, colabore y haga 
 *        mención al autor original.
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
