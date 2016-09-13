package ar.com.santalucia.servicio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.aplicacion.gestor.academico.GestorInscripcion;
import ar.com.santalucia.aplicacion.gestor.academico.GestorLlamado;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMateria;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMesa;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMesaExamenHist;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorBoletinNotasHist;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorPersonal;
import ar.com.santalucia.dominio.dto.DetallePreviaDTO;
import ar.com.santalucia.dominio.dto.InscripcionConsultaDTO;
import ar.com.santalucia.dominio.dto.InscripcionConsultaV2DTO;
import ar.com.santalucia.dominio.dto.InscripcionConsultaV2Detalle;
import ar.com.santalucia.dominio.dto.LlamadoDTO;
import ar.com.santalucia.dominio.dto.MateriaDTO;
import ar.com.santalucia.dominio.dto.MesaAltaDTO;
import ar.com.santalucia.dominio.dto.MesaDTO;
import ar.com.santalucia.dominio.modelo.academico.ActaVolanteExamenes;
import ar.com.santalucia.dominio.modelo.academico.Inscripcion;
import ar.com.santalucia.dominio.modelo.academico.Llamado;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.academico.Mesa;
import ar.com.santalucia.dominio.modelo.academico.MesaExamenHist;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotasHist;
import ar.com.santalucia.dominio.modelo.desempenio.MateriaNotasBoletin;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.Personal;
import ar.com.santalucia.excepciones.ValidacionException;
/**
 * Este servicio gestiona todo lo relacionado a meas, previas, llamados y calificaciones de mesas.
 * @author Ariel Ramirez
 * @version 1.0
 */
public class ServicioLlamadoAcademico {
	private GestorMateria gMateria;
	private GestorAlumno gAlumno;
	private GestorPersonal gDocente;
	private GestorLlamado gLlamado;
	private GestorMesa gMesa;
	private GestorMesaExamenHist gMEHist;
	private GestorBoletinNotasHist gBoletinHist;
	private GestorInscripcion gInscripcion;

 	public ServicioLlamadoAcademico() throws Exception {
		try {
			gMateria = new GestorMateria();
			gAlumno = new GestorAlumno();
			gDocente = new GestorPersonal();
			gLlamado = new GestorLlamado();
			gMesa = new GestorMesa();
			gMEHist = new GestorMesaExamenHist();
			gBoletinHist = new GestorBoletinNotasHist();
			gInscripcion = new GestorInscripcion();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar el servicio de operaciones básicas: "
					+ ex.getMessage());
		}
	}

 	public Boolean addMesa(MesaAltaDTO mesaAltaDTO) throws Exception{
		try{
			ValidacionException vEx = new ValidacionException();
			Mesa mesaPersis = null;
			Llamado llamado = gLlamado.getById(mesaAltaDTO.getIdLlamado());
			if(llamado!=null && !(llamado.getIdLlamado() == null)){
				// Verifico que la emsa esté comprendido en el periodo del llamado
				ServicioConfiguracion.comprendidoEnPeriodo(mesaAltaDTO.getFechaHoraInicio(), mesaAltaDTO.getFechaHoraFin(), null, llamado.getFechaInicio(), llamado.getFechaFin());
				//Busco los personal docente
				Personal docente1 = gDocente.getByExample(new Personal(mesaAltaDTO.getTribunalDoc1(),null,null,null,null,null,null,null,null,null,null,true,null,null,null,null)).get(0);
				Personal docente2 = gDocente.getByExample(new Personal(mesaAltaDTO.getTribunalDoc2(),null,null,null,null,null,null,null,null,null,null,true,null,null,null,null)).get(0);
				Personal docente3 = gDocente.getByExample(new Personal(mesaAltaDTO.getTribunalDoc3(),null,null,null,null,null,null,null,null,null,null,true,null,null,null,null)).get(0);
				// Localizo la materia
				Materia materia = gMateria.getById(mesaAltaDTO.getIdMateria());
				if (materia == null){
					vEx.addMensajeError("No existe la materia especificada.");
					throw vEx;
				}
				// Verifico entre las mesas del llamado si existe 
				Set<Mesa> mesasLlamado = llamado.getListaMesas();
				for(Mesa m : mesasLlamado){
					if(m.getMateria().equals(materia)){
						mesaPersis = m;
						break;
					}
				}
				//Pregunto que hacer segun la operación 
				if(mesaAltaDTO.getIdMesa() == null){  					// Mesa viene para ser nueva 
					if(mesaPersis != null){
						vEx.addMensajeError("La mesa ya existe.");		// Si ya existe corto la ejecución
						throw vEx;
					}
					// Preparo la entidad mesa a dar de alta y establezco los valores
					Mesa mesa = new Mesa();
					mesa.setFechaHoraInicio(mesaAltaDTO.getFechaHoraInicio());
					mesa.setFechaHoraFin(mesaAltaDTO.getFechaHoraFin());
					Set<Personal> tribunal = new HashSet<Personal>();
					tribunal.add(docente1);
					tribunal.add(docente2);
					tribunal.add(docente3);
					mesa.setIntegrantesTribunal(tribunal);
					mesa.setMateria(materia);
					gMesa.add(mesa);
					llamado.getListaMesas().add(mesa);
					gLlamado.modify(llamado);
				}else{ 													//Mesa viene para modificar
					if (mesaPersis == null){
						vEx.addMensajeError("No se puede modificar una mesa inexistente.");  //Si no existe corto la ejecución
						throw vEx;
					}
					mesaPersis.setFechaHoraInicio(mesaAltaDTO.getFechaHoraInicio());
					mesaPersis.setFechaHoraFin(mesaAltaDTO.getFechaHoraFin());
					Set<Personal> tribunal = new HashSet<Personal>();
					tribunal.add(docente1);
					tribunal.add(docente2);
					tribunal.add(docente3);
					mesaPersis.setIntegrantesTribunal(tribunal);
					mesaPersis.setMateria(materia);
					gMesa.modify(mesaPersis);
				}
			}else{
				vEx.addMensajeError("No se encontró el llamado.");
				throw vEx;
			}
		}catch(ValidacionException vEx){
			vEx.addMensajeError("Ocurrió un problema al intentar crear una mesa y asociarla al llamado.");
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
		return true;
	}

 	/**
	 * Genera una entidad Acta Volante Examen sin detalles, asociada a una mesa 
	 * @param idLlamado
	 * @param idMesa
	 * @throws ValidacionException
	 * @throws Exception
	 */
	public void generarActaVolanteExamen(Long idLlamado, Long idMesa) throws ValidacionException, Exception{
		try{
			ValidacionException vEx = new ValidacionException();
			ActaVolanteExamenes actaVolanteExamen = new ActaVolanteExamenes();
			Llamado llamado = new Llamado();
			Mesa mesa = new Mesa();
			llamado = getLlamado(idLlamado);       //Localizar llamado y validar 
			if (llamado.getIdLlamado() == null){
				vEx.addMensajeError("No se pudo encontrar el llamado.");
				throw vEx;
			}
			mesa = gMesa.getById(idMesa);         //Localizar mesa y validar
			if(mesa.getIdMesa() == null){
				vEx.addMensajeError("No se pudo encontrar la mesa");
				throw vEx;
			}
			
			List<Personal> tribunal = new ArrayList<Personal>(); //tomo los docentes del tribunal de la mesa
			tribunal.addAll(mesa.getIntegrantesTribunal());
			
			actaVolanteExamen.setCicloLectivo(Integer.valueOf(ServicioConfiguracion.getParametro("CICLO_LECTIVO").getValor()));
			actaVolanteExamen.setEstado(true);
			actaVolanteExamen.setIdLlamado(llamado.getIdLlamado());
			actaVolanteExamen.setNombreLlamado(llamado.getDescripcion());
			actaVolanteExamen.setIdMesa(mesa.getIdMesa());
			actaVolanteExamen.setNombreMesa(mesa.getMateria().getNombre());
			actaVolanteExamen.setFechaMesa(mesa.getFechaHoraInicio());
			actaVolanteExamen.setHoraFin(mesa.getFechaHoraFin());
			actaVolanteExamen.setTribunal1(tribunal.get(0));
			actaVolanteExamen.setTribunal2(tribunal.get(1));
			actaVolanteExamen.setTribunal3(tribunal.get(2));
			actaVolanteExamen.setModificable(true);
			actaVolanteExamen.setEstado(true);
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw new Exception("No se pudo crear el Acta Volante de Examen." + ex.getMessage());
		}
	}

	/**
	 * Agrega una inscripción a mesa (No agrega una entidad inscripción al menos que sea necesario)<br>
	 * Quita y agrega elementos del listado de Mesa de la entidad Inscripción si este existe.<br>
	 * No permite operar si no existe periodo de inscripción a Mesa vigente.
	 * @param idMesa
	 * @param idAlumno
	 * @return
	 * @throws Exception
	 */
	public Boolean addInscripcion(Long idMesa, Long idAlumno) throws Exception{
		try {
			Alumno alumno = (Alumno) gAlumno.getById(idAlumno);
			Mesa mesa = gMesa.getById(idMesa);
			if(mesa == null){
				ValidacionException ex = new ValidacionException();
				ex.addMensajeError("La mesa solicitada no existe.");
				throw ex;
			}
			Calendar fechaActual = Calendar.getInstance();
			Calendar fechaLlamadoInicio = Calendar.getInstance(); 
			Calendar fechaLlamadoFin = Calendar.getInstance();
			Llamado llamado = encontrarLlamadoVigente();  //Encuentra el llamado solo si está en periodo vigente de inscripcion
			
			
			if (llamado != null){
				Inscripcion inscripcion = buscarInscripcion(idAlumno, llamado.getIdLlamado());
				if (inscripcion == null){
					fechaLlamadoInicio.setTime(llamado.getFechaInicio());
					fechaLlamadoFin.setTime(llamado.getFechaFin());
					if ( fechaActual.equals(fechaLlamadoInicio) || fechaActual.before(fechaLlamadoInicio) ){ //Para verificar que no esté intentando inscibirse durante la mesa   			
						inscripcion = new Inscripcion();
						inscripcion.setCodigo(this.codigoSiguienteInscripcion(llamado.getIdLlamado()));
						inscripcion.setIdLlamado(llamado.getIdLlamado());
						inscripcion.setActivo(true);
						inscripcion.setAlumno(alumno);
						inscripcion.setFecha(fechaActual.getTime());
						gInscripcion.add(inscripcion); //Creo la inscripción sin lista de Mesas
						Set<Mesa> listaMesa = new HashSet<Mesa>();
						listaMesa.add(mesa);			
						inscripcion.setListaMesas(listaMesa);
						gInscripcion.modify(inscripcion);   //Aca se vincula la inscripcion con la mesa
					}
				}else{
					inscripcion.getListaMesas().add(mesa);
					gInscripcion.modify(inscripcion);
				}
			}else{
				ValidacionException ex = new ValidacionException();
				ex.addMensajeError("No existe periodo de inscripción a llamado vigente.");
				throw ex;
			}
		} 
		catch(ValidacionException ex){
			throw ex;
		}
		catch (Exception ex) {
			throw new Exception("No se pudo agregar la Inscripcion: " + ex.getMessage());
			//e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Elimina las inscripciones a mesa de un llamado. Si la lista de mesas inscriptas es cero se elimina la Inscripción.
	 * @param idMesa
	 * @param idAlumno
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteInscripcion(Long idMesa, Long idAlumno) throws Exception{
		try{
			Mesa mesa = gMesa.getById(idMesa);
			if (mesa == null){
				ValidacionException ex = new ValidacionException();
				ex.addMensajeError("La mesa solicitada no existe.");
				throw ex;
			}
			Llamado llamado = encontrarLlamadoVigente();  //Encuentra el llamado solo si está en periodo vigente de inscripcion
			
			if (llamado != null){
				Inscripcion inscripcion = buscarInscripcion(idAlumno, llamado.getIdLlamado());
				if (inscripcion != null){
					Set<Mesa> listaMesa = inscripcion.getListaMesas();
					if (listaMesa.contains(mesa)){
						listaMesa.remove(mesa);
						if (listaMesa.size() == 0 ){
							gInscripcion.delete(inscripcion);
						}else{
							inscripcion.setListaMesas(listaMesa);
							gInscripcion.modify(inscripcion);
						}
					}
				}else{
					ValidacionException ex = new ValidacionException();
					ex.addMensajeError("La inscripción no existe.");
					throw ex;
				}
			}else{
				ValidacionException ex = new ValidacionException();
				ex.addMensajeError("No existe periodo de inscripción a llamado vigente.");
				throw ex;
			}
		}catch (Exception ex){
			throw new Exception("No se pudo eliminar la inscripicón a mesa solicitada. " + ex.getMessage());
		}
		return true;
	}
	
	/**
	 * Lista las mesas a la que el alumno puede inscribirse, teniendo en cuenta las amterias previas del mismo.
	 * @param idAlumno
	 * @return
	 * @throws ValidacionException
	 * @throws Exception
	 */
	public List<InscripcionConsultaDTO> listarInscribibles(Long idAlumno) throws ValidacionException, Exception{
		try{
			ServicioAcademico sAcademico = new ServicioAcademico();
			Alumno alumno = (Alumno)gAlumno.getById(idAlumno); // Hubo un problema al obtener el alumno
			if (alumno == null){
				ValidacionException ex = new ValidacionException();
				ex.addMensajeError("No se ha encontrado el alumno.");
				throw ex;
			}			
			Llamado llamadoVigente = encontrarLlamadoVigente(); // No hay llamado vigente a la fecha
			if(llamadoVigente == null){
				ValidacionException ex = new ValidacionException();
				ex.addMensajeError("No existen llamados vigentes.");
				throw ex;
			}			
			List<DetallePreviaDTO> previas = getPreviasDesaprobadas(alumno.getNroDocumento()); // El alumno no tiene previas
			if(previas.size()==0){
				ValidacionException ex = new ValidacionException();
				ex.addMensajeError("No hay materias previas para inscribirse.");
				throw ex;
			}
			Set<Mesa> mesas = llamadoVigente.getListaMesas();           //Mesas del llamado vigente
			List<InscripcionConsultaDTO> inscribibles = new ArrayList<InscripcionConsultaDTO>();
			for(Mesa m: mesas){											//Por cada mesa, recorro las previas en busca de coincidencia de nombre de materia
				for(DetallePreviaDTO p: previas){
					String tribunal = new String();
					InscripcionConsultaDTO aux = new InscripcionConsultaDTO();
					if( (m.getMateria().getNombre()).equals(p.getNombreMateria()) ){ 
						Calendar calendar = Calendar.getInstance();
						aux.setAlumno(alumno.toString());
						aux.setIdMesa(m.getIdMesa());
						aux.setInscripto(false); // No se si está inscripto todavía, lo dejo en false
						DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
					    DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
						calendar.setTime(m.getFechaHoraInicio());
						aux.setFecha(formatoFecha.format(calendar.getTime()));
						calendar.setTime(m.getFechaHoraInicio());
						aux.setHoraInicio( formatoHora.format(calendar.getTime()) );
						calendar.setTime(m.getFechaHoraFin());
						aux.setHoraFin(formatoHora.format(calendar.getTime()) );
						aux.setMateria(m.getMateria().getNombre());
						aux.setDescripcionMateria(m.getMateria().getDescripcion());
						Set<Personal> iTribunal = m.getIntegrantesTribunal();
						for(Personal tri : iTribunal){
							tribunal = tribunal + tri.getApellido() +", "+tri.getNombre() + "; ";
						}
						aux.setTribunal(tribunal);
						
						List<MateriaDTO> materiasAux = sAcademico.getMateriasDTO();   //Capturo el nombre de la materia
						for(MateriaDTO mDTO : materiasAux){
							if(m.getMateria().getNombre().equals(mDTO.getNombre())){
								aux.setAnioMateria(mDTO.getAnio());
								break;
							}
						}
						
						inscribibles.add(aux);
					}
				}
			}
			if(inscribibles.size() == 0){									// Tiene previas, pero las mesas no están disponibles o la materia no existe más
				ValidacionException ex = new ValidacionException();
				String cadenaPrevias = new String();
				/*Iterator<DetallePreviaDTO> it = previas.iterator();
				while (it.hasNext()){
					cadenaPrevias = cadenaPrevias + " " +it.next().getNombreMateria()+" - ("+it.next().getAnio()+") | ";
				}*/
				for(DetallePreviaDTO i : previas){
					cadenaPrevias = cadenaPrevias + i.getNombreMateria()+" - ("+i.getAnio()+") | ";
				}
				ex.addMensajeError("No existen mesas disponibles para las previas encontradas: " + cadenaPrevias);
				throw ex;
			}
			
			Inscripcion inscripcion = buscarInscripcion(idAlumno, llamadoVigente.getIdLlamado());	// Busco las incripciones para ver el estado
			Set<Mesa> mesasInscriptas = new HashSet<Mesa>();
			
			if(inscripcion !=null){ // Si existe la inscripción tiene por lo menos una mesa adentro 
				mesasInscriptas = inscripcion.getListaMesas();
				for(Mesa minsc : mesasInscriptas){
					for(InscripcionConsultaDTO i : inscribibles){			// Seteo en true las que están inscriptas
						if(minsc.getIdMesa().equals(i.getIdMesa())){
							i.setInscripto(true);
						}
					}
				}
			}
			return inscribibles;
		}catch(ValidacionException ex){
			throw ex;
		}
		catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 *  Lista las mesas a la que el alumno puede inscribirse, teniendo en cuenta las amterias previas del mismo, en formato InscripcionConsultaV2DTO.
	 * @param dniAlumno
	 * @return
	 * @throws ValidacionException
	 * @throws Exception
	 */
	public InscripcionConsultaV2DTO listarInscribiblesV2(Long dniAlumno) throws ValidacionException, Exception{
	try{
		// Obtener el id del alumno
		// Obtener lista de inscribibles InscripcionConsultaDTO
		// formatear con el nuevo dto. Devolver.
		InscripcionConsultaV2DTO inscribiblesDTO = new InscripcionConsultaV2DTO();
		List<InscripcionConsultaV2Detalle> detalle = new ArrayList<InscripcionConsultaV2Detalle>();
	
		ServicioAlumno sAlumno = new ServicioAlumno();
		Alumno alumno = new Alumno();
		alumno = sAlumno.getUsuarioByDni(dniAlumno);
		if(alumno == null){
			ValidacionException vEx = new ValidacionException();
			vEx.addMensajeError("El dni de alumno solicitado no existe.");
			throw vEx;
		}
		List<InscripcionConsultaDTO> listado = new ArrayList<InscripcionConsultaDTO>();
		listado = listarInscribibles(alumno.getIdUsuario());
		inscribiblesDTO.setAlumno(alumno.toString());
		inscribiblesDTO.setIdAlumno(alumno.getIdUsuario());
		for(InscripcionConsultaDTO insDTO : listado){
			InscripcionConsultaV2Detalle itemDetalle = new InscripcionConsultaV2Detalle();
			itemDetalle.setAnioMateria(insDTO.getAnioMateria());
			itemDetalle.setDescripcionMateria(insDTO.getDescripcionMateria());
			itemDetalle.setFecha(insDTO.getFecha());
			itemDetalle.setHoraInicio(insDTO.getHoraInicio());
			itemDetalle.setHoraFin(insDTO.getHoraFin());
			itemDetalle.setIdMesa(insDTO.getIdMesa());
			itemDetalle.setInscripto(insDTO.getInscripto());
			itemDetalle.setMateria(insDTO.getMateria());
			itemDetalle.setTribunal(insDTO.getTribunal());
			detalle.add(itemDetalle);
		}
		inscribiblesDTO.setDetalle(detalle);
		return inscribiblesDTO;
		}catch(ValidacionException ex){
			throw ex;
		}
		catch(Exception ex){
			throw ex;
		}
	}
	
	public Boolean asignarMateriaAMesa(Materia materia, Long idMesa) throws Exception { // EN ENDPOINT
		try {
			Mesa mesa = gMesa.getById(idMesa);
			mesa.setMateria(materia);
			gMesa.modify(mesa);
			return true;
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar la MATERIA a la MESA: " + ex.getMessage());
		}
	}
	
	public Boolean desvincularMateriaDeMesa(Materia materia, Long idMesa) throws Exception { // EN ENDPOINT
		try {
			Mesa mesa = gMesa.getById(idMesa);
			mesa.setMateria(null);
			gMesa.modify(mesa);
			return true;
		} catch (Exception ex) {
			throw new Exception("No se pudo desvincular la MATERIA de la MESA: " + ex.getMessage());
		}
	}
	
	/**
	 * Obtiene sólo las previas que no han sido aprobadas aún.
	 * @param numeroDni
	 * @param nombreMateria
	 * @return
	 */
	public List<DetallePreviaDTO> getPreviasDesaprobadas(Long numeroDni) throws Exception{
		// Recorrer el listado de DTOs en busca de nota mayor o igual a 6
		// Si nota es mayor a 6, agregar al listado de aprobadas
		// Luego, por cada aprobada eliminar del listado original de Previas (previa sobrecarga del equals, atributo anio y nombremateria)
		
		// Preparo la lista para las aprobadas
		List<DetallePreviaDTO> listaAprobadas = new ArrayList<DetallePreviaDTO>();
		// Pido a getPrevias que traiga el historial de desaprobadas e histórico de previas
		List<DetallePreviaDTO> listaPreviaDto = getPrevias(numeroDni);
		for (DetallePreviaDTO dpdto : listaPreviaDto){
			if (dpdto.getNota() != null) {
				if (dpdto.getNota() >= 6) {
					listaAprobadas.add(dpdto);
				}
			}
		}
		// Quito las aprobadas del listado de previa
		if(!listaAprobadas.isEmpty()){
			listaPreviaDto.removeAll(listaAprobadas);
		}
		// Elimino los duplicados, si existiera
		Set<DetallePreviaDTO> setListadoDevolver = new HashSet<DetallePreviaDTO>();
		if (!listaPreviaDto.isEmpty()) {
			for (DetallePreviaDTO dpdto : listaPreviaDto) {
				dpdto.setNota(null);
				dpdto.setFechaInscripcion(null);
				dpdto.setAsistencia(null);
				setListadoDevolver.add(dpdto);
			}
		}
		List<DetallePreviaDTO>listaPreviaDtoDevolver = new ArrayList<DetallePreviaDTO>();
		listaPreviaDtoDevolver.addAll(setListadoDevolver);
		return listaPreviaDtoDevolver;
	}
	
	/**
	 * Obtiene todas las previas y la historia de su rendida, desde el momento que se debió la materia hasta que la aprobó.
	 * @param numeroDni
	 * @return
	 * @throws Exception
	 */
	public ArrayList<DetallePreviaDTO> getPrevias(Long numeroDni) throws Exception{
		Float promedio = 0F;
		ArrayList<DetallePreviaDTO> listadoPrevias = new ArrayList<DetallePreviaDTO>();
		//DetallePreviaDTO detallePreviaDTO = new DetallePreviaDTO();
		BoletinNotasHist boletin = new BoletinNotasHist();
		// CAPTURO LOS BOLETINES HISTÓRICOS DEL ALUMNO
		boletin.setDniAlumno(numeroDni);
		ArrayList<BoletinNotasHist> listaBoletines = gBoletinHist.getByExample(boletin);
		// INICIALIZO PARA EL ARREGLO DE NOTAS TRIMESTRALES (FILA MATERIA, NOTAS TRIMESTRES)
		Set<MateriaNotasBoletin> listaMnb = new HashSet<MateriaNotasBoletin>();
		for (BoletinNotasHist bnh : listaBoletines){
			listaMnb = bnh.getListaMateriasNotasBoletin();
			// RECORRO CADA bhn BoletinNotaHistorico
			for(MateriaNotasBoletin mnb : listaMnb){
				promedio = (float) (mnb.getNotaTrimestre1() + mnb.getNotaTrimestre2() + mnb.getNotaTrimestre3())/3F;
				if( (mnb.getNotaTrimestre3() < 6) || (promedio < 6) ){
					if ( (mnb.getNotaDiciembre()==null) || (mnb.getNotaDiciembre() < 6) ) {
						if( (mnb.getNotaMarzo()==null) || mnb.getNotaMarzo() < 6) {
							// HAY QUE BUSCAR EL REGISTRO HISTÓRICO DE "RENDIDAS"
							ArrayList<MesaExamenHist> listadoMEH = obtenerHistoricoRendidas(numeroDni, mnb.getMateria(), bnh.getCicloLectivo());
							// Para comprobar si lo rindió alguna vez, se busca en el historico de mesas
							// Si no lo rindió nunca (historico vacio) entonces se toman los datos de la libreta
							if (!listadoMEH.isEmpty()) {
								for (MesaExamenHist lmeh : listadoMEH) {
									DetallePreviaDTO detallePreviaDTO = new DetallePreviaDTO(); // Lo puse aca porque sino se reescribía el objeto
									detallePreviaDTO.setNombreMateria(lmeh.getNombreMateria());
									detallePreviaDTO.setCicloLectivoMateria(lmeh.getCicloLectivoMateria());
									detallePreviaDTO.setDniAlumno(lmeh.getDniAlumno());
									detallePreviaDTO.setAnio(lmeh.getAnio());
									detallePreviaDTO.setAsistencia(lmeh.getAsistencia());
									detallePreviaDTO.setFechaInscripcion(lmeh.getFechaInscripcion());
									detallePreviaDTO.setNota(lmeh.getNota());
									listadoPrevias.add(detallePreviaDTO);
								}
							}else{
								DetallePreviaDTO detallePreviaDTO = new DetallePreviaDTO();
								detallePreviaDTO.setNombreMateria(mnb.getMateria());
								detallePreviaDTO.setCicloLectivoMateria(bnh.getCicloLectivo());
								detallePreviaDTO.setDniAlumno(bnh.getDniAlumno());
								detallePreviaDTO.setAnio(bnh.getAnio());
								listadoPrevias.add(detallePreviaDTO);
							}
						}
					}
				}
			}
		}
		return listadoPrevias;
	};
	
	/**
	 * Devuelve un listado de llamado en formato DTO
	 * @return
	 * @throws ValidacionException
	 * @throws Exception
	 */
	public List<LlamadoDTO> listarLlamados() throws ValidacionException, Exception{
		try{
			List<LlamadoDTO> llamadosDTO = new ArrayList<LlamadoDTO>();
			List<Llamado> llamados = new ArrayList<Llamado>();
			llamados = gLlamado.List();  //
			for(Llamado ll : llamados){
				LlamadoDTO llamadoDTO = new LlamadoDTO();
				List<MesaDTO> mesasDTO = new ArrayList<MesaDTO>();
				for(Mesa mesa: ll.getListaMesas()){
					MesaDTO mDTO = new MesaDTO();
					mDTO.setFechaHoraFin(mesa.getFechaHoraFin());
					mDTO.setFechaHoraInicio(mesa.getFechaHoraInicio());
					mDTO.setIdMateria(mesa.getMateria().getIdMateria());
					mDTO.setIdMesa(mesa.getIdMesa());
					mDTO.setMateria(mesa.getMateria().getNombre());
					Set<Personal> personal = mesa.getIntegrantesTribunal();
					Integer count = 0;
					for (Personal p : personal){  // Cargo los integrantes de tribunal
						count = count+1;
						switch (count) {
						case 1:
							mDTO.setIdTribunal1(p.getIdUsuario());
							mDTO.setNombreTribunal1(p.toString());
							break;
						case 2:
							mDTO.setIdTribunal2(p.getIdUsuario());
							mDTO.setNombreTribunal2(p.toString());
							break;
						case 3:
							mDTO.setIdTribunal3(p.getIdUsuario());
							mDTO.setNombreTribunal3(p.toString());
							break;
						}
					}
					mesasDTO.add(mDTO);
				}
				llamadoDTO.setDescripcion(ll.getDescripcion());
				llamadoDTO.setFechaFin(ll.getFechaFin());
				llamadoDTO.setFechaInicio(ll.getFechaInicio());
				llamadoDTO.setListaMesas(mesasDTO);
				llamadoDTO.setVigente(false);
				llamadoDTO.setIdLlamado(ll.getIdLlamado());
				llamadosDTO.add(llamadoDTO);
			}
		Llamado llamadoVigente = encontrarLlamadoVigente();
		if (llamadoVigente != null){
			for(LlamadoDTO ll : llamadosDTO){
				if(ll.getIdLlamado().equals(llamadoVigente.getIdLlamado())){  //Seteo en true si el llamado encontrado está vigente
					ll.setVigente(true);
				}
			}
		}
		return llamadosDTO;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * Encuentra el llamado vigente a la fecha según la fecha actual del servidor.
	 * @return
	 * @throws Exception
	 */
	private Llamado encontrarLlamadoVigente() throws Exception{
		try{
		
		Calendar fechaActual = Calendar.getInstance(); //Quemar fecha comprendida en periodo
		Calendar periodoInicio = Calendar.getInstance();
		Calendar periodoFin = Calendar.getInstance();
		
		Long diasVigencia = Long.valueOf(ServicioConfiguracion.getParametro("VIS_DIAS_LLAMADO").getValor());
		
		ArrayList<Llamado> listaLlamado = gLlamado.List();
		for(Llamado ll: listaLlamado){
			periodoInicio.setTime(ll.getFechaInicio());
			periodoInicio.add(Calendar.DAY_OF_MONTH, - diasVigencia.intValue()); // Comienzo del periodo segun fecha de llamado
			periodoFin.setTime(ll.getFechaInicio());
			periodoFin.add(Calendar.DAY_OF_MONTH, -2); // Fin de periodo de inscripcion
			if( ( fechaActual.equals(periodoInicio) || fechaActual.after(periodoInicio) ) 
				&& ( fechaActual.equals(periodoFin) || fechaActual.before(periodoFin) ) ){
				return ll;
			}
		}
		}catch(Exception ex){
			throw ex;
		}
		return null;
	}
	
	/**
	 * Encuentra todas las inscripciones realizada por el alumno.
	 * @param idAlumno
	 * @param idLlamado
	 * @return
	 * @throws Exception
	 */
	private Inscripcion buscarInscripcion(Long idAlumno, Long idLlamado) throws Exception{
		try{
			String sql = new String();
			sql = "select * from INSCRIPCION WHERE ALUMNO = " + idAlumno;		
			Session sessAux = null;
			if ((sessAux == null) || (!sessAux.isOpen())) {
				sessAux = HibernateUtil.getSessionFactory().openSession();	
			}
			if (!sessAux.getTransaction().isActive()) {
				sessAux.beginTransaction();
			}
			SQLQuery consulta = sessAux.createSQLQuery(sql).addEntity(Inscripcion.class);

			List result = consulta.list();
			sessAux.close();
			if (result.size() != 0) {
				List<Inscripcion> listaInscripcion = result;
				for(Inscripcion i : listaInscripcion){
					if (i.getIdLlamado() == idLlamado){
						return i;
					}
					return null; // Existen inscripciones, pero no para este llamado
				}
			} else {
				return null; // No existen inscripciones para este llamado
			}
		}catch (Exception ex){
			throw ex;
		}
		return null;
	} 
	/**
	 * Devuelve el código de la siguiente inscripcion.
	 * @param idLlamado
	 * @return
	 */
	private Integer codigoSiguienteInscripcion(Long idLlamado) throws Exception{
		try {
			String sql = new String();
			sql = "SELECT MAX(INSCRIPCION.CODIGO) AS VALOR FROM INSCRIPCION WHERE IDLLAMADO = " + idLlamado;
			Session sessAux = null;
			if ((sessAux == null) || (!sessAux.isOpen())) {
				sessAux = HibernateUtil.getSessionFactory().openSession();	
			}
			if (!sessAux.getTransaction().isActive()) {
				sessAux.beginTransaction();
			}
			SQLQuery consulta = sessAux.createSQLQuery(sql);
			Integer result = (Integer) consulta.uniqueResult();
			
			if ( result == null ){
				return 1;
			}else{
				return result + 1;
			}
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Obtiene registros históricos de rendidas del alumno.
	 * @param numeroDni
	 * @param nombreMateria
	 * @param cicloLectivoMateria
	 * @return
	 * @throws Exception
	 */
	private ArrayList<MesaExamenHist> obtenerHistoricoRendidas(Long numeroDni, String nombreMateria, Integer cicloLectivoMateria) throws Exception{
		MesaExamenHist ejemplo = new MesaExamenHist();
		ejemplo.setDniAlumno(numeroDni);
		ejemplo.setCicloLectivoMateria(cicloLectivoMateria);
		if(!nombreMateria.equals("")){
			ejemplo.setNombreMateria(nombreMateria);
		}
		return (ArrayList<MesaExamenHist>) gMEHist.getByExample(ejemplo);
	}
	
	public Boolean addLlamado(Llamado llamado) throws Exception { // EN ENDPOINT
		try {
			ServicioConfiguracion.comprendidoEnPeriodo(llamado.getFechaInicio(), llamado.getFechaFin(),null,null,null);
			if (llamado.getIdLlamado() == null) {
				gLlamado.add(llamado);
			}
			else {
				gLlamado.modify(llamado);
			}
		} catch (ValidacionException ex) {
			throw ex;			
		} catch (Exception ex) {
			throw new Exception("No se pudo dar de alta el LLAMADO: " + ex.getMessage());
		}
		return true;
	}
	
	public Boolean deleteLlamado(Llamado llamado) throws Exception{ // EN ENDPOINT
		try {
			gLlamado.delete(llamado);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar el LLAMADO: " + ex.getMessage());
		}
		return true;
	}

	public Llamado getLlamado(Long idLlamado) throws Exception { // EN ENDPOINT
		try {
			return gLlamado.getById(idLlamado);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener el LLAMADO: " + ex.getMessage());
		}
	}
	
	public Llamado getLlamado(String descLlamado) throws Exception{
		return getLlamados(new Llamado(null,descLlamado,null,null,null)).get(0);
	}
	
	public ArrayList<Llamado> getLlamados(Llamado example) throws Exception{ // EN ENDPOINT
		try {
			return gLlamado.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener el listado de LLAMADOS: " + ex.getMessage());
		}
	}
}
