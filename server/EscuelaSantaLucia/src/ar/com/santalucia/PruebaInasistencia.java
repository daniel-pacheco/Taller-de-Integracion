package ar.com.santalucia;

import java.util.Date;

import ar.com.santalucia.aplicacion.gestor.desempenio.GestorInasistencia;
import ar.com.santalucia.dominio.modelo.desempenio.Inasistencia;
import ar.com.santalucia.excepciones.ValidacionException;

public class PruebaInasistencia {

	public static void main(String[] args) throws Exception {
		Inasistencia inasistencia1 = new Inasistencia();
		Inasistencia inasistencia2 = new Inasistencia();
		Inasistencia inasistencia3 = new Inasistencia();
		GestorInasistencia gInasistencia = new GestorInasistencia();
		
		inasistencia1.setIdInasistencia(null);
		inasistencia1.setCantidad(0.5F);
		inasistencia1.setConcepto("Llegada tarde");
		inasistencia1.setFecha(new Date());
		inasistencia1.setJustificada(true);
		
		try {
			gInasistencia.add(inasistencia1);
		} catch (ValidacionException e) {
			System.out.println(e.getMessage());
		}
		
//		inasistencia2.setIdInasistencia(null);
//		inasistencia2.setCantidad(1F);
//		inasistencia2.setConcepto("Ed.Física");
//		inasistencia2.setFecha(new Date());
//		inasistencia2.setJustificada(true);
//
//		try {
//			gInasistencia.add(inasistencia2);
//		} catch (ValidacionException e) {
//			System.out.println(e.getMessage());
//		}
//
//		inasistencia3.setIdInasistencia(null);
//		inasistencia3.setCantidad(0.5F);
//		inasistencia3.setConcepto("Llegada tarde");
//		inasistencia3.setFecha(new Date());
//		inasistencia3.setJustificada(true);
//
//		try {
//			gInasistencia.add(inasistencia3);
//		} catch (ValidacionException e) {
//			System.out.println(e.getMessage());
//		}
		
	}

}
