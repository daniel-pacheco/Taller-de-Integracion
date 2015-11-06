package ar.com.santalucia.login;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class Authorizer implements SecurityContext {

	@Override
	public String getAuthenticationScheme() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Principal getUserPrincipal() {
		// TODO Auto-generated method stub
		return new Principal(){

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUserInRole(String role) {
		// TODO Auto-generated method stub
		return true;
	}

}
