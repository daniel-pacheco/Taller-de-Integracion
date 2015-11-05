package ar.com.santalucia.login;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import ar.com.santalucia.rest.ServicioAlumnoEndpoint;

@ApplicationPath("/")
public class EscuelaSantaLuciaResourceConfig extends ResourceConfig {
	public EscuelaSantaLuciaResourceConfig(){
		super(ServicioAlumnoEndpoint.class);
		register(RolesAllowedDynamicFeature.class);
		register(AuthorizationFilter.class);
	}
}
