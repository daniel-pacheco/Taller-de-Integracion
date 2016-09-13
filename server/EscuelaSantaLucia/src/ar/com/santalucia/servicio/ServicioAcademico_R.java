package ar.com.santalucia.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ar.com.santalucia.aplicacion.gestor.academico.GestorAnio;
import ar.com.santalucia.aplicacion.gestor.academico.GestorArea;
import ar.com.santalucia.aplicacion.gestor.academico.GestorCurso;
import ar.com.santalucia.aplicacion.gestor.academico.GestorEspecialidad;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMateria;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMateriaHist;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMesa;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorPersonal;
import ar.com.santalucia.dominio.dto.AnioDTO;
import ar.com.santalucia.dominio.dto.CursoDTO;
import ar.com.santalucia.dominio.dto.MateriaAltaDTO;
import ar.com.santalucia.dominio.dto.MateriaDTO;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Area;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Especialidad;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.academico.MateriaHist;
import ar.com.santalucia.dominio.modelo.academico.Mesa;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.Personal;
import ar.com.santalucia.excepciones.ValidacionException;

/**
 * Clase servicio para gestión académica. Engloba Anio, Curso, alumnos y
 * materias
 * 
 * @author ericpennachini
 *
 */

// Último modificador: Ariel Ramirez @ 12-09-2016

public class ServicioAcademico_R {

	private GestorAnio gAnio;
	private GestorCurso gCurso;
	private GestorMateria gMateria;
	private GestorArea gArea;
	private GestorPersonal gDocente;
	private GestorMesa gMesa;
	private GestorMateriaHist gMateriaHistorica;
	private GestorEspecialidad gEspecialidad;

 	public ServicioAcademico_R() throws Exception {
		try {
			gAnio = new GestorAnio();
			gCurso = new GestorCurso();
			gMateria = new GestorMateria();
			gArea = new GestorArea();
			gDocente = new GestorPersonal();
			gMesa = new GestorMesa();
			gMateriaHistorica = new GestorMateriaHist();
			gEspecialidad = new GestorEspecialidad();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar el servicio de operaciones básicas: "
					+ ex.getMessage());
		}
	}

	public Boolean addAnio(Anio anio) throws ValidacionException, Exception {	// EN ENDPOINT
		try {
			ValidacionException vEx = new ValidacionException();	// Inicio Bloque verificación de especialidad
			if(anio.getEspecialidad() == null){
				vEx.addMensajeError("No se puede cargar un año sin especialidad. La especialidad es obligatoria.");
				throw vEx;
			}else{
				Especialidad aux = gEspecialidad.getById(anio.getEspecialidad().getIdEspecialidad()); //
				if(aux == null){
					vEx.addMensajeError("La especialidad no existe.");
					throw vEx;
				}
			}														// Fin Bloque verificación de especialidad
			
			if (anio.getIdAnio() == null) {
				anio.setCicloLectivo(ServicioConfiguracion.getParametro("CICLO_LECTIVO").getValor());
				gAnio.add(anio);
			}
			else {
				Anio anioAux = new Anio();
				anioAux = getAnio(anio.getIdAnio());
				if ( ! ((anioAux.getNombre()).equals(anio.getNombre())) || ! ((anioAux.getCicloLectivo()).equals(anio.getCicloLectivo())) ){
					crearMateriaHist(anioAux);
				}
				gAnio.modify(completarAnioPersistente(anio));
			}
			
		} 
		catch(ValidacionException ex){
			throw ex;
		}catch(NullPointerException ex){
			throw new Exception("No se pudo dar de alta el AÑO");
		}
		catch (Exception ex) {
			throw new Exception("No se pudo dar de alta el AÑO: " + ex.getMessage());
		}
		
		return true;
	}

	public Boolean deleteAnio(Anio anio) throws Exception {  // EN ENDPOINT
		// TODO
		// Elimina un año.
		// ATENCIÓN: ¡La operacion puede ser en cascada!
		try {
			gAnio.delete(anio);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar el AÑO: " + ex.getMessage());
		}
		return true;
	}

	public Anio getAnio(Long idAnio) throws Exception { 	// EN ENDPOINT
		try {
			return gAnio.getById(idAnio);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener el AÑO: " + ex.getMessage());
		}
	}

	public List<Anio> list() throws Exception{				// EN ENDPOINT
		return getAnios(new Anio());
	} 
	
	public List<Anio> getAnios(Anio example) throws Exception { // EN ENDPOINT
		try {
			return gAnio.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la lista de AÑOS: " + ex.getMessage());
		}
	}
	
	
	public ArrayList<AnioDTO> getAniosDTO() throws Exception {
		try {
			ArrayList<Anio> listaAniosPersis = gAnio.getByExample(new Anio());
			ArrayList<AnioDTO> listaAniosDTO = new ArrayList<AnioDTO>();
			
			for (Anio a : listaAniosPersis) {
				AnioDTO aDTO = new AnioDTO();
				aDTO.setIdAnio(a.getIdAnio());
				aDTO.setNombre(a.getNombre());
				aDTO.setDescripcion(a.getDescripcion());
				aDTO.setEspecialidad(a.getEspecialidad().getNombre());
				aDTO.setOrden(a.getOrden());
				aDTO.setCicloLectivo(a.getCicloLectivo());
				aDTO.setActivo(a.getActivo());
				Set<Curso> listaCursosAnioPersis = a.getListaCursos();
				for (Curso c : listaCursosAnioPersis) {
					CursoDTO cDTO = new CursoDTO();
					cDTO.setIdCurso(c.getIdCurso());
					cDTO.setDivision(c.getDivision().toString());
					cDTO.setTurno(c.getTurno());
					cDTO.setCantAlu(c.getListaAlumnos().size());
					aDTO.getListaCursos().add(cDTO);
				}
				listaAniosDTO.add(aDTO);
			}
			
			return listaAniosDTO;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al obtener la lista de años resumida: " + ex.getMessage());
		}
	}
	

	public Boolean addCurso(Curso curso, Long idAnio) throws Exception { // CANDIDATO A SUPRIMIR
		// TODO
		// 1 - Obtener el objeto año
		// 2 - Extraemos el listado de curso
		// 3 - Crear el objeto curso nuevo
		// 4 - Agregar el curso al listado
		// 5 - Modificar el año con el nuevo listado de curso
		// 6 - LLamar a modify del gestor del año con el año que se le agregó el
		// curso
		try {
			if (curso.getIdCurso() == null) {
				Anio anio = new Anio();
				anio = gAnio.getById(idAnio);
				Set<Curso> cursos = anio.getListaCursos();
				cursos.add(curso);
				gCurso.add(curso);
				anio.setListaCursos(cursos);
				gAnio.modify(anio);
				return true;
			} 
			else {
				gCurso.modify(curso);
				return true;
			}
		} catch (Exception ex) {
			throw new Exception("No se pudo agregar el curso al AÑO: " + ex.getMessage());
		}
	}

	/**
	 * Borra un Curso asignando los alumnos de este al curso genérico.
	 * @param curso
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteCurso(Curso curso) throws Exception { 	// EN ENDPOINT
		try {
			Set<Alumno> alumnosCursoBorrado = curso.getListaAlumnos();
			Curso cursoGenerico = gCurso.getByDivision('0');
			cursoGenerico.getListaAlumnos().addAll(alumnosCursoBorrado); // atencion, hay que ver si suma realmentelos datos
			gCurso.modify(cursoGenerico);
			gCurso.delete(curso);
			return true;
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar el CURSO: " + ex.getMessage());
		}
	}

	public Curso getCurso(Long idCurso) throws Exception { 		// EN ENDPOINT
		try {
			return gCurso.getById(idCurso);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener el CURSO: " + ex.getMessage());
		}
	}
	
	public Boolean addMateria(MateriaAltaDTO materiaAltaDTO) throws Exception {
		ServicioAlumnadoAcademico sAlumnadoAcademico = new ServicioAlumnadoAcademico();
		ValidacionException vEx = new ValidacionException();
		// DECLARO AUXILIARES
		Area areaAux = new Area();
		Anio anioAux = new Anio();
		Materia materiaAux = new Materia();
		Personal docenteTitular = new Personal();
		Personal docenteSuplente = new Personal();
		
		// VERIFICO EXISTENCIA DEL ANIO. SI NO SE ENCUENTRA LANZO UNA EXCEPCIÓN 
		anioAux = gAnio.getById(materiaAltaDTO.getIdAnio());
		if (anioAux == null){
			vEx.addMensajeError("SERVICIO: No se pudo dar de alta la materia. No se encontró el año.");
			throw vEx;
		}
		// TOMO EL AREA, SI ES NULL, CARGA NULL Y NO HAY PROBLEMA
		areaAux = materiaAltaDTO.getArea();
		
		//VERIFICO PROBLEMAS CON EL NOMBRE DE MATERIA
		if(materiaAltaDTO.getIdMateria() == null){                                                   // COMPRUEBA SOLO PARA CASOS DE ALTA NUEVA
			if(existeMateriaEnAnio(false,materiaAltaDTO.getNombreMateria(), materiaAltaDTO.getIdAnio())){
				vEx.addMensajeError("El nombre de materia ya existe para el año especificado.");
				throw vEx;
			}
		}else{
			if(existeMateriaEnAnio(true,materiaAltaDTO.getNombreMateria(), materiaAltaDTO.getIdAnio())){
				vEx.addMensajeError("El nombre de materia ya existe para el año especificado.");
				throw vEx;
			}
		}
		
		// BUSQUEDA DE DOCENTES (POR ID)
		if(materiaAltaDTO.getIdDocenteTitular() != null){
			docenteTitular = (Personal) gDocente.getById(materiaAltaDTO.getIdDocenteTitular());
			materiaAux.setDocenteTitular(docenteTitular);
		}else{
			materiaAux.setDocenteTitular(null);
		}
		if (materiaAltaDTO.getIdDocenteSuplente() != null){
			docenteSuplente = (Personal) gDocente.getById(materiaAltaDTO.getIdDocenteSuplente());
			materiaAux.setDocenteSuplente(docenteSuplente);
		}else{
			materiaAux.setDocenteSuplente(null);
		}
		// ARMO LA MATERIA PARA PERSISTIR
		materiaAux.setIdMateria(materiaAltaDTO.getIdMateria());			//PUEDE SER NULL O VENIR CON VALOR (MODIFY)
		materiaAux.setNombre(materiaAltaDTO.getNombreMateria());
		materiaAux.setDescripcion(materiaAltaDTO.getDescripcion());
		materiaAux.setArea(areaAux);
		materiaAux.setActivo(materiaAltaDTO.getActivo());
		if(materiaAux.getIdMateria() != null){						// SI YA EXISTE LA MATERIA
			if(materiaAltaDTO.getIdAnio() != null){					// Y ADEMÁS VIENE CON AÑO
				desvincularMateriaDeAnio(materiaAux, materiaPerteneceAnio(materiaAux));	// SI EXISTE LA MATERIA, VIENE CON AÑO, ENTONCES DESVINCULO ANTES
				asignarMateriaAAnio(materiaAux, materiaAltaDTO.getIdAnio());			// VUELVO A VINCULAR AL NUEVO AÑO
			}
			gMateria.modify(materiaAux);
			return true;
		}else{														// SI NO EXISTE LA MATERIA CREO Y VINCULO
			gMateria.add(materiaAux);
			asignarMateriaAAnio(materiaAux, materiaAltaDTO.getIdAnio());
			sAlumnadoAcademico.actualizarBoletinNotas(materiaAux);
			return true;
		} 
	}
	
	/**
	 * Determina si existe el nombre de una materia o no en un Año.
	 * @param materia
	 * @param idAnio
	 * @return
	 * @throws ValidacionException
	 * @throws Exception
	 */
	private Boolean existeMateriaEnAnio(Boolean modificacion, String materia, Long idAnio) throws ValidacionException, Exception{
		try{
			ValidacionException vEx = new ValidacionException();
			Integer contador = 0;
			Anio anio = this.getAnio(idAnio);
			if (anio == null){
				vEx.addMensajeError("No se pudo encontrar el año.");
				throw vEx;
			}
			Set<Materia> listaMaterias = anio.getListaMaterias(); 
			for(Materia m : listaMaterias){
				if (m.getNombre().equals(materia)){
					contador++;
				}
			}
			if(modificacion == false){
				return (contador>=1 ? true : false);	
			}else{
				return (contador>=2 ? true : false);	
			}
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	public Boolean deleteMateria(Materia materia) throws Exception { // EN ENDPOINT
		try {
			ServicioAlumnadoAcademico sAlumnadoAcademico = new ServicioAlumnadoAcademico();
			sAlumnadoAcademico.actualizarBoletinNotasBaja(materia);
			gMateria.delete(materia);
			return true;
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar la MATERIA: " + ex.getMessage());
		}
	}

	public Materia getMateria(Long idMateria) throws Exception{ // EN ENDPOINT
		try{
			return gMateria.getById(idMateria);
		} catch(Exception ex){
			throw new Exception("No se pudo obtener la MATERIA: " + ex.getMessage());
		}
	}
	
	public List<Materia> getMaterias(Materia example) throws Exception{
		try {
			return gMateria.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la lista de MATERIAS: " + ex.getMessage());
		}
	}
	
	public ArrayList<MateriaDTO> getMateriasDTO() throws Exception {
		try {
			ArrayList<Materia> listaMateriasPersis = new ArrayList<Materia>(gMateria.List());
			ArrayList<MateriaDTO> listaMateriasDto = new ArrayList<MateriaDTO>();
			
			for (Materia mp : listaMateriasPersis) {
				MateriaDTO materiaDTO = new MateriaDTO();
				//BEGIN logica para llenar campos
				materiaDTO.setIdMateria(mp.getIdMateria());
				materiaDTO.setNombre(mp.getNombre());
				materiaDTO.setDescripcion(mp.getDescripcion());
				materiaDTO.setDocenteTitular((mp.getDocenteTitular() != null)
												? mp.getDocenteTitular().toString()
												:"");
				materiaDTO.setDocenteSuplente((mp.getDocenteSuplente() != null)
												? mp.getDocenteSuplente().toString()
												:"");
				materiaDTO.setArea(mp.getArea().getNombre());
				for (Anio a : this.getAnios(new Anio())) { // recupero la lista completa de años
					if (a.getListaMaterias().contains(mp)) {
						materiaDTO.setAnio(a.getNombre());
					}
				}
				//END logica para llenar campos
				listaMateriasDto.add(materiaDTO);
			}
			
			return listaMateriasDto;
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la lista DTO de MATERIAS: " + ex.getMessage());
		}
	}
	
	public MateriaDTO getMateriaDTO(Long idMateria) throws Exception{
		try{
			List<MateriaDTO> listadoCompleto = getMateriasDTO();
			for(MateriaDTO mDTO : listadoCompleto){
				if(mDTO.getIdMateria().equals(idMateria)){
					return mDTO;
				}
			}
			return null;
		}catch (Exception ex){
			throw ex;
		}
	}
	
	public Boolean asignarDocentesAMateria(Personal docenteTitular, Personal docenteSuplente, Long idMateria) throws Exception { // EN ENDPOINT
		// TODO
		// 1 - Obtener la materia con el gestor
		// 2 - Asignar docente titular y suplente haciedo
		// materia.setDocente(Docente t)
		// 3 - Llamar al metodo modify del gestor de materia con la misma
		// modificada
		// Sugerencia: si docente que llega es null, no actualizar llamando a
		// materia.setDocente(Docente t)
		try {
			Materia materia = gMateria.getById(idMateria);
			if (docenteTitular != null) {
				materia.setDocenteTitular(docenteTitular);
			}
			if (docenteSuplente != null) {
				materia.setDocenteSuplente(docenteSuplente);
			}
			gMateria.modify(materia);
			return true;
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar DOCENTES a la MATERIA: " + ex.getMessage());
		}
	}
	
	public Boolean desvincularDocentesDeMateria(Personal docenteTitular, Personal docenteSuplente, Long idMateria) throws Exception {
		try {
			Materia materia = gMateria.getById(idMateria);
			if (docenteTitular != null) {
				materia.setDocenteTitular(null);
			}
			if (docenteSuplente != null) {
				materia.setDocenteSuplente(null);
			}
			gMateria.modify(materia);
			return true;
		} catch (Exception ex) {
			throw new Exception("No se pudo desvincular DOCENTES de la MATERIA: " + ex.getMessage());
		}
	}

	public Boolean asignarMateriaAAnio(Materia materia, Long idAnio) throws Exception { // EN ENDPOINT
		// TODO
		// 1 - Obtener el año
		// 2 - Rescatar la lista de materias del año
		// 3 - Agregar la materia a la lista rescatada
		// 4 - Asignar la lista al año llamando a set()
		// 5 - Llamar al modify de gestor de año
		try {
			Anio anio = gAnio.getById(idAnio);
			Set<Materia> materias = anio.getListaMaterias();
			materias.add(materia);
			anio.setListaMaterias(materias);
			gAnio.modify(anio);
			return true;
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar la MATERIA al AÑO: " + ex.getMessage());
		}
	}

	public Boolean desvincularMateriaDeAnio(Materia materia, Long idAnio) throws Exception { // EN ENDPOINT 
		// TODO
		// 1 - Obtener el año
		// 2 - Rescatar la lista de materias
		// 3 - Remover la materia de esa lista
		// 4 - Asignar mediante set() la lista al año
		// 5 - Llamar al modify del gestor de año y guardarlo  
		try {
			Anio anio = gAnio.getById(idAnio);
			Set<Materia> materias = anio.getListaMaterias();
			materias.remove(materia);
			anio.setListaMaterias(materias);
			gAnio.modify(anio);
		} catch(Exception ex) {
			throw new Exception("No se pudo desvincular la MATERIA al AÑO: " + ex.getMessage());
		}
		return true;
	}
	
 	
	public Boolean addArea(Area area) throws Exception {  // EN ENDPOINT
		try{
			if(area.getIdArea() == null) {
				gArea.add(area);	
			} else {
				gArea.modify(area);
			}
		} catch(Exception ex) {
			throw new Exception("No se pudo dar de alta el ÁREA: " + ex.getMessage());
		}
		return true;
	}
	
	public Boolean deleteArea(Area area) throws Exception { // EN ENDPOINT
		try{
			gArea.delete(area);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar el ÁREA: " + ex.getMessage());
		}
		return true;
	}
	
	public Area getArea(Long idArea) throws Exception { // EN ENDPOINT
		try {
			return gArea.getById(idArea);
		} catch(Exception ex) {
			throw new Exception("No se pudo obtener el ÁREA: " + ex.getMessage());
		}
	}
	
	public ArrayList<Area> getAreas(Area example) throws Exception{ // EN ENDPOINT
		try{
			return gArea.getByExample(example);
		}catch(Exception ex){
			throw new Exception("No se pudo obtener el listado de ÁREAS: " + ex.getMessage());
		}
	}
	
	
	public Boolean addEspecialidad(Especialidad especialidad) throws Exception, ValidacionException{
		try{
			if(especialidad.getIdEspecialidad() == null){
				gEspecialidad.add(especialidad);
				return true;
			}else{
				gEspecialidad.modify(especialidad); 
				return true;
			}
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	
	public Boolean deleteEspecialidad(Especialidad especialidad) throws Exception, ValidacionException{
		try{
			especialidadOcupada(especialidad);
			gEspecialidad.delete(especialidad);
			return true;
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw new Exception("No se pudo eliminar el ÁREA: " + ex.getMessage());
		}
	}
	
	public Especialidad getEspecialidadById(Long idEspecialidad) throws Exception{
		try{
			return gEspecialidad.getById(idEspecialidad);
		}catch(Exception ex){
			throw new Exception("No se pudo obtener la ESPECIALIDAD: " + ex.getMessage());
		}
	}
	
	public ArrayList<Especialidad> getEspecialidad(Especialidad example) throws Exception{
		try{
			return gEspecialidad.getByExample(example);
		}catch(Exception ex){
			throw new Exception("No se pudo obtener el listado de ESPECIALIDADES: " + ex.getMessage());
		}
	}

	
	/**
	 * Verifica si una especialidad está siendo ocupada por algún año.
	 * @param especialidad
	 * @throws Exception
	 * @throws ValidacionException
	 */
	private void especialidadOcupada(Especialidad especialidad) throws Exception, ValidacionException{
		try{
			ValidacionException vEx = new ValidacionException();
			List<Anio> listadoAnio = gAnio.getByExample(new Anio(null,null,null,null,null,null,null,null,true));
			if(listadoAnio!= null && listadoAnio.size() > 0){
				for (Anio a : listadoAnio){
					if(a.getEspecialidad().equals(especialidad)){
						vEx.addMensajeError(a.getNombre());
					}
				}
			}
			if(vEx.getMensajesError().size() > 0){
				throw vEx;
			}
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	
	//SUPRIMIR ? 12-09-2016
	public Boolean addMesa(Mesa mesa) throws Exception{ // EN ENDPOINT
		try {
			if (mesa.getIdMesa() == null) {
				gMesa.add(mesa);
			} else {
				gMesa.modify(mesa);
			}
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new Exception("No se pudo dar de alta la MESA: " + ex.getMessage());
		}
		return true;
	}
	
	public Mesa getMesa(Long idMesa) throws Exception { // EN ENDPOINT
		try {
			return gMesa.getById(idMesa);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtner la MESA: " + ex.getMessage());
		}
	}
	
	public List<Mesa> getMesas(Mesa example) throws Exception{ // EN ENDPOINT
		try {
			return gMesa.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtner las MESAS: " + ex.getMessage());
		}
	}
	
	public Boolean deleteMesa(Mesa mesa) throws Exception { // EN ENDPOINT
		try {
			gMesa.delete(mesa);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar la MESA: " + ex.getMessage());
		}
		return true;
	}
	
	
	
	
	// ##### MÉTODOS AUXILIARES #####
	
	/**
	 * Crear un registro histórico en MateriaHist.
	 * @param elemento
	 * @throws Exception
	 */
	private void crearMateriaHistorica(Object elemento) throws Exception{
		if(elemento instanceof Materia){  
			crearMateriaHist((Materia)elemento); 						// Si se recibe una Materia
		}else{
			if(elemento instanceof Anio){
				crearMateriaHist((Anio)elemento); 					// Si se recibe un Año
			}else{
				throw new Exception("No se pudo crear el histórico");
			}
		}
	}
	
	/**
	 * Crear un registro histórico en MateriaHist si se recibe una Materia.
	 * @param materia
	 * @return
	 * @throws Exception
	 */
	private Boolean crearMateriaHist(Materia materia) throws Exception{
		ServicioAlumnadoAcademico sAlumnadoAcademico = new ServicioAlumnadoAcademico();
		/* Cargamos datos de la materia al histórico */
		MateriaHist materiaHistorica = new MateriaHist();
		materiaHistorica.setNombreMateria(materia.getNombre());
		materiaHistorica.setDescripcionMateria(materia.getDescripcion());
		/* Localizar el año */
		Anio anio = sAlumnadoAcademico.encontrarAnioDeMateria(materia); // Método nuevo
		if (anio != null){
			materiaHistorica.setNombreAnio(anio.getNombre());
			materiaHistorica.setCicloLectivo(anio.getCicloLectivo());
			gMateriaHistorica.add(materiaHistorica); // Persistir
			return true;
		}
		return false;	
	}
	
	/**
	 * Crear un registro histórico en MateriaHist si se recibe un Anio.
	 * @param anio
	 * @return
	 * @throws Exception
	 */
	private Boolean crearMateriaHist(Anio anio) throws Exception{
		Set<Materia> listaMaterias = anio.getListaMaterias();
		MateriaHist materiaHistorica = new MateriaHist();
		materiaHistorica.setNombreAnio(anio.getNombre());
		materiaHistorica.setCicloLectivo(anio.getCicloLectivo());
		for (Materia m:listaMaterias){
			materiaHistorica.setNombreMateria(m.getNombre());
			materiaHistorica.setDescripcionMateria(m.getDescripcion());
			gMateriaHistorica.add(materiaHistorica);
		}
		return true;
	}
	
	/**
	 * Realiza la modificación de una entidad Anio que viene con datos propios, no agregados.
	 * @param anioModif
	 * @return Entidad Anio completa desde la entidad persistente, con los campos indicados modificados.
	 * @throws Exception
	 */
	private Anio completarAnioPersistente(Anio anioModif) throws Exception{
		// RECUPERAR POR ID EL AÑO COMPLETO
		// MODIFICAR LOS DATOS
		// DEVOLVER LA ENTIDAD MODIFICADA
		Anio anioAux = this.getAnio(anioModif.getIdAnio()); 	// Obtengo el persistente (no copio el id porque ya viene con el objeto)
		anioAux.setNombre(anioModif.getNombre());				// Copio los datos de la modificación al persistente copiado
		anioAux.setDescripcion(anioModif.getDescripcion());
		anioAux.setCicloLectivo(anioModif.getCicloLectivo());
		anioAux.setEspecialidad(anioModif.getEspecialidad());
		anioAux.setActivo(anioModif.getActivo());
		return anioAux;
	}
	
	
	
	private Long materiaPerteneceAnio(Materia materia) throws Exception{
		// Devolver el año al que pertenece la materia
		// Obtener los años activo
		// Eecorrer las materias y devolver el id de año (si se encuentra)
		List<Anio>listaAnio = new ArrayList<Anio>();
		List<Materia> listaMateria = new ArrayList<Materia>(); 
		listaAnio = gAnio.getByExample(new Anio(null,null,null,null,null,null, null, null, true));
		for(Anio a: listaAnio){
			if(a.getListaMaterias().contains(materia)){
				return a.getIdAnio();
			}
		}
		return 0L; // DEVUELVE 0 SI LA MATERIA NO PERTENECE A UN ANIO
	}
	
	private Anio cursoPerteneceAnio(Curso curso) throws Exception {
		List<Anio> listaAnio = new ArrayList<Anio>();
		List<Curso> listaCurso = new ArrayList<Curso>(); 
		listaAnio = gAnio.getByExample(new Anio(null,null,null,null,null,null, null, null, true));
		for (Anio a: listaAnio) {
			if(a.getListaCursos().contains(curso)){
				return a;
			}
		}
		return null;
	}
	
	/**
	 * Obtiene el orden de un anio propocionando el id de Anio
	 * @param idAnio
	 * @return
	 * @throws Exception
	 */
	public Integer ObtenerOrdenAnio(Long idAnio) throws Exception{
		try{
			if(idAnio == 0){
				return 0;	//Si es id Cero, devuelvo Cero
			}
			List<Anio> todosLosAnios = getAnios(new Anio(null,null,null,null,null,null,null,null,null));
			for(Anio a : todosLosAnios){
				if(a.getIdAnio().equals(idAnio)){
					return a.getOrden();
				}
			}
		}catch(Exception ex){
			throw ex;
		}
		return 0; //No se encontró id y se devuelve Cero
	}
	
	/**
	 * Obtiene el orden del anio proporcionando el id de Curso
	 * @param idCurso
	 * @return
	 * @throws Exception
	 */
	public Integer ObtenerOrdenAnioCurso(Long idCurso) throws Exception{
		try{
			if(idCurso == 0){
				return 0;	//Si es id Cero, devuelvo Cero
			}
			List<Anio> todosLosAnios = getAnios(new Anio(null,null,null,null,null,null,null,null,null));
			for(Anio a : todosLosAnios){
				for(Curso c : a.getListaCursos()){
					if(c.getIdCurso().equals(idCurso)){
						return a.getOrden();
					}
				}
			}
		}catch(Exception ex){
			throw ex;
		}
		return 0;
	}
	
	//public void ObtenerInscripcionDTO()
	
	//public void listarInscripcionesDTO()
	
	public void closeSession() throws Exception { 
		gAnio.closeSession();
	}
	
}