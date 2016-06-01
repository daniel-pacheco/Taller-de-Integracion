package ar.com.santalucia.rest.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class EscuelaSantaLucia extends ResourceConfig {
	public EscuelaSantaLucia(){
		super();
		register(RolesAllowedDynamicFeature.class);
	}
}
