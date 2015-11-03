package ar.com.santalucia.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/authentication")
public class AuthenticationEndpoint {
	@POST
	@Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
	public Response autenticateUser(@FormParam("username") String username, 
									@FormParam("password") String password) {
		try {

            // Authenticate the user using the credentials provided
            authenticate(username, password);

            // Issue a token for the user
            String token = issueToken(username);

            // Return the token on the response
            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }      
	}
	
	public void authenticate(String username, String password) {
		
	}
	
	public String issueToken(String username) {
		
		
		return null;
	}
	
}
