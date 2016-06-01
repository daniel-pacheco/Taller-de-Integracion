package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.academico.CursoHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.excepciones.ValidacionException;

/**
 * Clase gestor de cursos
 * 
 * @author ericpennachini
 * @version 1.1
 *
 */

// UltimoModificador: Eric Pennachini @ 23-10-2015 16:23

public class GestorCurso extends Gestor<Curso> implements IListable<Curso> {

	private CursoHome cursoDAO;
	private GestorAlumno GAlumno;
	
	public GestorCurso() throws Exception {
		super();
		try {
			cursoDAO = new CursoHome();
			if (cursoDAO.findByExample(new Curso(null,'0',null,null,null)).size() == 0) {
				Curso cursoGen = new Curso();
				cursoGen.setDivision('0');
				cursoGen.setTurno("Generico");
				cursoDAO.persist(cursoGen);
			}
			GAlumno = new GestorAlumno();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(Curso object) throws Exception {
		try {
			//this.validar(object);
			setSession();
			setTransaction();
			cursoDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar el CURSO: " + ex.getMessage());
		}
	}

	@Override
	public void modify(Curso object) throws Exception {
		try {
			setSession();
			setTransaction();
			//this.validar(object);
			cursoDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el CURSO: " + ex.getMessage());
		}
	}

	@Override
	public void delete(Curso object) throws Exception {
		try {
			setSession();
			setTransaction();
			cursoDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar el CURSO: " + ex.getMessage());
		}
	}

	@Override
	public Curso getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Curso cursoDevolver = new Curso();
			cursoDevolver = cursoDAO.findById(id);
			return cursoDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el CURSO por su ID: " + ex.getMessage());
		}
	}
	
	/*
	public ArrayList<Curso> getByExample(Curso example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Curso> listaCursosDevolver = (ArrayList<Curso>) cursoDAO.findByExample((Curso) example);
			sesionDeHilo.getTransaction().commit();
			return listaCursosDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar CURSOS que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}
	
	public ArrayList<Curso> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Curso criterioVacio = new Curso();
			ArrayList<Curso> listaCursosDevolver = new ArrayList<Curso>();
			listaCursosDevolver = (ArrayList<Curso>) cursoDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaCursosDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los CURSOS: " + ex.getMessage());
		}
	}
	*/
	
	public Curso getByDivision(Character division) throws Exception{
		try {
			setSession();
			setTransaction();
			Curso cursoEjemplo = new Curso();
			cursoEjemplo.setDivision(division);
			ArrayList<Curso> listaCursosDevolver = this.getByExample(cursoEjemplo);
			for (Curso c: listaCursosDevolver) {
				return c; //directamente return en el primero, porque va a ser el unico
			}
			return null;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el CURSO por su división: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Curso> getByExample(Curso example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Curso> listaCursosDevolver = (ArrayList<Curso>) cursoDAO.findByExample((Curso) example);
			sesionDeHilo.getTransaction().commit();
			return listaCursosDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar CURSOS que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Curso> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Curso criterioVacio = new Curso();
			ArrayList<Curso> listaCursosDevolver = new ArrayList<Curso>();
			listaCursosDevolver = (ArrayList<Curso>) cursoDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaCursosDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los CURSOS: " + ex.getMessage());
		}
	}


	/*
	public void validar(Curso object) throws Exception {
		// TODO Auto-generated method stub
		
	}
*/
}
