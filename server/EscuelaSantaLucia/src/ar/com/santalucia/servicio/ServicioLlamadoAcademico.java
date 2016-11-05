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

import com.sun.faces.facelets.tag.jsf.ValidatorTagHandlerDelegateImpl;
import com.sun.management.GarbageCollectionNotificationInfo;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.aplicacion.gestor.academico.GestorActaVolanteExamenes;
import ar.com.santalucia.aplicacion.gestor.academico.GestorDetalleVolante;
import ar.com.santalucia.aplicacion.gestor.academico.GestorInscripcion;
import ar.com.santalucia.aplicacion.gestor.academico.GestorLlamado;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMateria;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMesa;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMesaExamenHist;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorBoletinNotasHist;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorPersonal;
import ar.com.santalucia.dominio.dto.ActaVolanteExamenesDTO;
import ar.com.santalucia.dominio.dto.ComprobanteInscripcionMesaDTO;
import ar.com.santalucia.dominio.dto.DetalleActaVolanteDTO;
import ar.com.santalucia.dominio.dto.DetallePreviaDTO;
import ar.com.santalucia.dominio.dto.InscripcionConsultaDTO;
import ar.com.santalucia.dominio.dto.InscripcionConsultaV2DTO;
import ar.com.santalucia.dominio.dto.InscripcionConsultaV2Detalle;
import ar.com.santalucia.dominio.dto.LlamadoDTO;
import ar.com.santalucia.dominio.dto.MateriaDTO;
import ar.com.santalucia.dominio.dto.MenuActaVolanteDTO;
import ar.com.santalucia.dominio.dto.MenuActaVolanteLlamadoDTO;
import ar.com.santalucia.dominio.dto.MenuActaVolanteMesaDTO;
import ar.com.santalucia.dominio.dto.MesaAltaDTO;
import ar.com.santalucia.dominio.dto.MesaDTO;
import ar.com.santalucia.dominio.modelo.academico.ActaVolanteExamenes;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.DetalleVolante;
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
	private GestorActaVolanteExamenes gActaVolanteExamenes;
	private GestorDetalleVolante gDetalleVolante;

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
			gActaVolanteExamenes = new GestorActaVolanteExamenes();
			gDetalleVolante = new GestorDetalleVolante();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar el servicio de operaciones básicas: "
					+ ex.getMessage());
		}
	}
 	/**
 	 * Agrega o modifica una mesa. En este método se hacen las llamadas a GenerarActaVolante() y ActualizarActaVolante() para nuevas mesas o actualización respectivamente
 	 * @param mesaAltaDTO
 	 * @return
 	 * @throws Exception
 	 */
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
					generarActaVolanteExamen(llamado.getIdLlamado(), mesa.getIdMesa());
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
					ActualizarActaVolante(llamado, mesaPersis, tribunal);
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
 	 * Obtiene una entidad Mesa completa dado su id.
 	 * @param idMesa
 	 * @return
 	 * @throws Exception
 	 */
 	public Mesa getMesa(Long idMesa) throws Exception { // EN ENDPOINT
		try {
			return gMesa.getById(idMesa);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtner la MESA: " + ex.getMessage());
		}
	}
 	
 	/**
 	 * Elimina físicamente una Mesa dado su id.
 	 * @param mesa
 	 * @return
 	 * @throws Exception
 	 */
 	public Boolean deleteMesa(Mesa mesa) throws Exception {
		try {
			gMesa.delete(mesa);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar la MESA: " + ex.getMessage());
		}
		return true;
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
						agregarDetalleDeActaVolante(idMesa, llamado.getIdLlamado(), idAlumno);
					}
				}else{
					inscripcion.getListaMesas().add(mesa);
					gInscripcion.modify(inscripcion);
					agregarDetalleDeActaVolante(idMesa, llamado.getIdLlamado(), idAlumno);
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
						quitarDetalleDeActaVolante(idMesa, llamado.getIdLlamado(), idAlumno);
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
	 * Devuelve un DTO de Mesa dado un ID de mesa
	 * @param idMesa
	 * @return
	 * @throws ValidacionException
	 * @throws Exception
	 */
	public MesaDTO getMesaDTO(Long idMesa) throws ValidacionException,Exception{
		try{
			Mesa mesa = new Mesa();
			MesaDTO mesaDTO = new MesaDTO(); 
			mesa = gMesa.getById(idMesa);
			List<Personal> tribunal = new ArrayList<Personal>();
			tribunal.addAll(mesa.getIntegrantesTribunal());
			mesaDTO.setIdMesa(mesa.getIdMesa());
			mesaDTO.setIdMateria(mesa.getMateria().getIdMateria());
			mesaDTO.setIdTribunal1(tribunal.get(0).getIdUsuario());
			mesaDTO.setIdTribunal2(tribunal.get(1).getIdUsuario());
			mesaDTO.setIdTribunal3(tribunal.get(2).getIdUsuario());
			mesaDTO.setMateria(mesa.getMateria().getNombre());
			mesaDTO.setNombreTribunal1(tribunal.get(0).toString());
			mesaDTO.setNombreTribunal2(tribunal.get(1).toString());
			mesaDTO.setNombreTribunal3(tribunal.get(2).toString());
			mesaDTO.setFechaHoraFin(mesa.getFechaHoraFin());
			mesaDTO.setFechaHoraInicio(mesa.getFechaHoraInicio());
			return mesaDTO;
		}catch(ValidacionException vEx){
			throw vEx;
		}catch (Exception ex){
			throw ex;
		}
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
	
	/**
	 * Devuelve el contenido del menú seleccionable en la ventana de calificación de Mesas.
	 * @throws ValidacionException
	 * @throws Exception
	 */
	public List<MenuActaVolanteDTO> devolverListadoMenuActaVolante() throws ValidacionException, Exception{
		return obtenerListadoMenuActaVolante();
	}
	
	/**
	 * Recorre las actas volantes disponibles y devuelve los datos para cargar en el menú seleccionable.  
	 * @throws ValidacionException
	 * @throws Exception
	 */
	public List<MenuActaVolanteDTO> obtenerListadoMenuActaVolante() throws ValidacionException, Exception{
		ValidacionException vEx = new ValidacionException();
		List<ActaVolanteExamenes> listaActaVolante = new ArrayList<ActaVolanteExamenes>();
		Set<Integer>ciclosLectivos = new HashSet<Integer>(); // Auxiliar Set
		List<MenuActaVolanteDTO> menuActaVolanteExamenesDTO = new ArrayList<MenuActaVolanteDTO>(); 
		listaActaVolante = gActaVolanteExamenes.getByExample(new ActaVolanteExamenes(null,null,null,null,null,null,null,null,null,null,null,null,null,null, true,null));
		if (listaActaVolante.size() == 0){ vEx.addMensajeError("No hay actas volantes que mostrar"); throw vEx;}
		for (ActaVolanteExamenes actaVolante : listaActaVolante){
			if (ciclosLectivos.add(actaVolante.getCicloLectivo())){
				menuActaVolanteExamenesDTO.add(new MenuActaVolanteDTO(actaVolante.getCicloLectivo(),null));
			}
		}
		for(MenuActaVolanteDTO menuActaVolanteDTO : menuActaVolanteExamenesDTO){
			menuActaVolanteDTO.setLlamadosActaDTO(obtenerActaVolanteLlamadoDTO(menuActaVolanteDTO.getCicloLectivo()));
		}
		return menuActaVolanteExamenesDTO;
	}
	
	/**
	 * Carga y devuelve un DTO ActaVolanteExamenesDTO solicitado por medio del id de ActaVolante
	 * @param idActaVolante
	 * @return
	 * @throws ValidacionException
	 * @throws Exception
	 */
	public ActaVolanteExamenesDTO getActaVolanteDTO(Long idActaVolante) throws ValidacionException, Exception{
		try {
			ActaVolanteExamenesDTO actaVolanteExamenDTO = new ActaVolanteExamenesDTO();
			ActaVolanteExamenes actaVolanteExamen = new ActaVolanteExamenes();
			List<DetalleActaVolanteDTO> detalleActaVolanteDTO = new ArrayList<DetalleActaVolanteDTO>();
			actaVolanteExamen = gActaVolanteExamenes.getById(idActaVolante);
			for(DetalleVolante detalleVolante : actaVolanteExamen.getDetalles()){
				DetalleActaVolanteDTO detalleVolanteDTO = new DetalleActaVolanteDTO();
				detalleVolanteDTO.setAlumno(detalleVolante.getAlumno().toString());
				detalleVolanteDTO.setAsistencia(detalleVolante.getAsistencia());
				detalleVolanteDTO.setIdDetalleVolante(detalleVolante.getIdDetalleVolante());
				detalleVolanteDTO.setNota(detalleVolante.getNota()==null ? 0 : detalleVolante.getNota()); 
				detalleActaVolanteDTO.add(detalleVolanteDTO);
			}
			actaVolanteExamenDTO.setDatosTribunal1(actaVolanteExamen.getTribunal1().toString());
			actaVolanteExamenDTO.setDatosTribunal2(actaVolanteExamen.getTribunal2().toString());
			actaVolanteExamenDTO.setDatosTribunal3(actaVolanteExamen.getTribunal3().toString());
			actaVolanteExamenDTO.setDetalleActaVolante(detalleActaVolanteDTO);
			actaVolanteExamenDTO.setFechaMesa(actaVolanteExamen.getFechaMesa());
			actaVolanteExamenDTO.setHoraFin(actaVolanteExamen.getHoraFin());
			actaVolanteExamenDTO.setHoraInicio(actaVolanteExamen.getHoraInicio());
			actaVolanteExamenDTO.setIdActaVolanteExamen(actaVolanteExamen.getIdActaVolanteExamen());
			actaVolanteExamenDTO.setNombreLlamado(actaVolanteExamen.getNombreLlamado());
			actaVolanteExamenDTO.setNombreMesa(actaVolanteExamen.getNombreMesa());
			return actaVolanteExamenDTO;
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	public ComprobanteInscripcionMesaDTO generarComprobanteInscripcionMesa(Long idAlumno, Long idMesa, Long idLlamado) throws Exception {
		ServicioAcademico sAcademico = new ServicioAcademico();
		ComprobanteInscripcionMesaDTO cbte = new ComprobanteInscripcionMesaDTO();
		Inscripcion inscripcion = new Inscripcion();
		inscripcion = buscarInscripcion(idAlumno, idLlamado);
		Mesa mesa = new Mesa();
		
		for (Mesa m : inscripcion.getListaMesas()) {
			if (m.getIdMesa().equals(idMesa)) {
				mesa = m;
				break;
			}
		}
		cbte.setAnio(sAcademico.getMateriaDTO(mesa.getMateria().getIdMateria()).getAnio());
		cbte.setMateria(mesa.getMateria().getNombre());
		cbte.setFechaHoraInicioMesa(mesa.getFechaHoraInicio());
		cbte.setFechaHoraFinMesa(mesa.getFechaHoraFin());
		Alumno alumno = (Alumno) gAlumno.getById(idAlumno);
		cbte.setNombreAlumno(alumno.getNombre());
		cbte.setApellidoAlumno(alumno.getApellido());
		cbte.setDniAlumno(alumno.getNroDocumento());
		Llamado llamado = gLlamado.getById(idLlamado);
		cbte.setPeriodo(llamado.getDescripcion());
		
		cbte.setFechaHoraActual(Calendar.getInstance().getTime());
		
		// generar en endpoint el usuario que lo genera
		
		return cbte;
	}
	
	/**
	 * Recibe datos de un acta volante de examen y los hace persisitentes.
	 * @param actaVolanteExamen
	 * @throws ValidacionException
	 * @throws Exception
	 */
	public Boolean calificarEnMesa(ActaVolanteExamenesDTO actaVolanteExamen, Long dniUsuario) throws ValidacionException, Exception{
		try{
			ValidacionException vEx = new ValidacionException();
			ActaVolanteExamenes actaVolante = new ActaVolanteExamenes();
			actaVolante = gActaVolanteExamenes.getById(actaVolanteExamen.getIdActaVolanteExamen());
			if (actaVolante.getIdActaVolanteExamen() == null){
				vEx.addMensajeError("No se pudo realizar la calificación. Acta volante no encontrada.");
				throw vEx;
			}
			if (validarCambios(actaVolanteExamen.getDetalleActaVolante()) == false ){
				return false;
			}; // Comprueba que se hayan insertado notas y su valor sea coherente
			cancelarHistoricosActaVolante(actaVolanteExamen.getIdActaVolanteExamen());
			ActaVolanteExamenes actaVolanteNuevo = generarActaVolanteExamenPreexistente(actaVolante, actaVolanteExamen.getDetalleActaVolante()); //El acta volante (necesito la cabecera)
			volcarDatosAHistoricos(actaVolanteNuevo);
			actaVolante.setModificable(false);
			actaVolante.setEstado(false);
			gActaVolanteExamenes.modify(actaVolante);
			return true;
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	
	// ---------------------- MÉTODOS AUXILIARES PRIVADOS, PÚBLICOS y PACKAGE ---------------------------------------
	
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
	
	/**
	 * Genera una entidad Acta Volante Examen sin detalles, asociada a una mesa
	 * @param idLlamado
	 * @param idMesa
	 * @throws ValidacionException
	 * @throws Exception
	 */
	private void generarActaVolanteExamen(Long idLlamado, Long idMesa) throws ValidacionException, Exception{
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
			actaVolanteExamen.setHoraInicio(mesa.getFechaHoraInicio());
			actaVolanteExamen.setHoraFin(mesa.getFechaHoraFin());
			actaVolanteExamen.setAnio(devolverNombreAnioDeMateria(mesa.getMateria().getIdMateria()));
			actaVolanteExamen.setTribunal1(tribunal.get(0));
			actaVolanteExamen.setTribunal2(tribunal.get(1));
			actaVolanteExamen.setTribunal3(tribunal.get(2));
			actaVolanteExamen.setModificable(true);
			actaVolanteExamen.setEstado(true);
			gActaVolanteExamenes.add(actaVolanteExamen);
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw new Exception("No se pudo crear el Acta Volante de Examen." + ex.getMessage());
		}
	}
	
	/**
	 * Devuelve el nombre de un Año proporcionando el identificador de Materia.
	 * @param idMateria
	 * @return
	 * @throws Exception
	 */
	private String devolverNombreAnioDeMateria(Long idMateria) throws Exception{
		ValidacionException vEx = new ValidacionException();
		Long idAnio;
		ServicioAcademico sAcademico = new ServicioAcademico();
		Materia materia = new Materia();
		Anio anio = new Anio();
		materia = gMateria.getById(idMateria);
		if (materia==null){
			 vEx.addMensajeError("No se pudo completar la búsqueda. ServicioLlamadoAcademico.");
			 throw vEx;
		}
		idAnio = sAcademico.materiaPerteneceAnio(materia);
		anio = sAcademico.getAnio(idAnio);
		if (anio==null){
			 vEx.addMensajeError("No se pudo completar la búsqueda. ServicioLlamadoAcademico.");
			 throw vEx;
		}
		return anio.getNombre();
	}
	
	/**
	 * Localiza una entidad ActaVolanteExamenes. Es utilizado por AddMesa, en modificar.
	 * @param idLlamado
	 * @param idMesa
	 * @return
	 * @throws ValidacionException
	 * @throws Exception
	 */
	private ActaVolanteExamenes localizarActaVolanteExamen(Long idLlamado, Long idMesa) throws ValidacionException, Exception{
		try{
			ValidacionException vEx = new ValidacionException();
			List<ActaVolanteExamenes> listaActaVolanteExamenes = new ArrayList<ActaVolanteExamenes>();
			listaActaVolanteExamenes = gActaVolanteExamenes.getByExample(new ActaVolanteExamenes(null,idLlamado,null,idMesa,null,null,null,null,null,null,null,null,null,null, true,null)); 
			if (listaActaVolanteExamenes.size() > 1){
				vEx.addMensajeError("Ocurrió un error al recuperar el Acta Volante de Examen. Resultado mayor a 1");
				throw vEx;
			}
			return listaActaVolanteExamenes.get(0);
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * Coloca en estado cancelado un Acta Volante de Examenes
	 * @param actaVolanteExamenes
	 * @throws ValidacionException
	 * @throws Exception
	 */
	private void cancelarActaVolante(ActaVolanteExamenes actaVolanteExamenes) throws ValidacionException, Exception{
		try{
			actaVolanteExamenes.setEstado(false);
			gActaVolanteExamenes.modify(actaVolanteExamenes);
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * Actualiza la entidad Acta Volante de Examenes asociada al llamado y mesa solicitada.<br>
	 * En caso de que el estado del Acta no permita modificacion (modificable = false), se solicitará la creación de un nuevo Acta.
	 * @param llamado
	 * @param mesa
	 * @param tribunal
	 * @throws ValidacionException
	 * @throws Exception
	 */
	private void ActualizarActaVolante(Llamado llamado, Mesa mesa, Set<Personal> tribunal) throws ValidacionException, Exception{
		try {
			ValidacionException vEx = new ValidacionException();
			ActaVolanteExamenes actaVolanteExamenesPersistente = null;
			List<Personal> tribunalList = new ArrayList<Personal>();
			tribunalList.addAll(tribunal);
			actaVolanteExamenesPersistente = localizarActaVolanteExamen(llamado.getIdLlamado(), mesa.getIdMesa());
			if (actaVolanteExamenesPersistente == null){
				vEx.addMensajeError("No se encontró el acta volante asociado con el llamado "+llamado.getIdLlamado().toString() +" y la mesa "+mesa.getIdMesa());
				throw vEx;
			}
			if (actaVolanteExamenesPersistente.getModificable() == true){
				actaVolanteExamenesPersistente.setCicloLectivo(Integer.valueOf(ServicioConfiguracion.getParametro("CICLO_LECTIVO").getValor()));
				actaVolanteExamenesPersistente.setEstado(true);
				actaVolanteExamenesPersistente.setIdLlamado(llamado.getIdLlamado());
				actaVolanteExamenesPersistente.setNombreLlamado(llamado.getDescripcion());
				actaVolanteExamenesPersistente.setIdMesa(mesa.getIdMesa());
				actaVolanteExamenesPersistente.setNombreMesa(mesa.getMateria().getNombre());
				actaVolanteExamenesPersistente.setFechaMesa(mesa.getFechaHoraInicio());
				actaVolanteExamenesPersistente.setHoraInicio(mesa.getFechaHoraInicio());
				actaVolanteExamenesPersistente.setHoraFin(mesa.getFechaHoraFin());
				actaVolanteExamenesPersistente.setTribunal1(tribunalList.get(0));
				actaVolanteExamenesPersistente.setTribunal2(tribunalList.get(1));
				actaVolanteExamenesPersistente.setTribunal3(tribunalList.get(2));
				actaVolanteExamenesPersistente.setModificable(true);
				actaVolanteExamenesPersistente.setEstado(true);
				gActaVolanteExamenes.modify(actaVolanteExamenesPersistente);
			}else{
				ActaVolanteExamenes nuevoActaVolanteExamenes = new ActaVolanteExamenes();   // Cancelar, copiar el detalle y generar el nuevo acta
				cancelarActaVolante (actaVolanteExamenesPersistente);
				generarActaVolanteExamen(llamado.getIdLlamado(), mesa.getIdMesa());
				nuevoActaVolanteExamenes = localizarActaVolanteExamen(llamado.getIdLlamado(), mesa.getIdMesa());
				nuevoActaVolanteExamenes.setDetalles(actaVolanteExamenesPersistente.getDetalles());
			}	
		} catch (ValidacionException vEx) {
			throw vEx;
		} catch (Exception ex){
			throw ex;
		}
	}
	
	/**
	 * Obtiene todos los llamados del Acta Volante para un ciclo lectivo específico. Este método es usado por obtenerListadoMenuActaVolante() 
	 * @param cicloLectivo
	 * @return
	 * @throws ValidacionException
	 * @throws Exception
	 */
	private List<MenuActaVolanteLlamadoDTO> obtenerActaVolanteLlamadoDTO(Integer cicloLectivo) throws ValidacionException, Exception{
		List<ActaVolanteExamenes> listaActaVolante = new ArrayList<ActaVolanteExamenes>();
		Set<String> llamados = new HashSet<String>(); // Auxiliar Set
		listaActaVolante = gActaVolanteExamenes.getByExample(new ActaVolanteExamenes(null,null,null,null,null,null,null,null,null,null,null,null, cicloLectivo,null,true,null)); //Localiza las actas volantes para un ciclo lectivo
		List<MenuActaVolanteLlamadoDTO>listaLlamadosActaVolante = new ArrayList<MenuActaVolanteLlamadoDTO>();
		for(ActaVolanteExamenes actaVolante : listaActaVolante){
			if(llamados.add(actaVolante.getNombreLlamado())){
				listaLlamadosActaVolante.add(new MenuActaVolanteLlamadoDTO(actaVolante.getNombreLlamado(),null));
			}
		}
		for (MenuActaVolanteLlamadoDTO menuActaVolanteLlamadoDTO : listaLlamadosActaVolante){
			menuActaVolanteLlamadoDTO.setMesas(obtenerVolanteMesaDTO(cicloLectivo, menuActaVolanteLlamadoDTO.getNombreLlamado()));
		}
		return listaLlamadosActaVolante;
	}
		
	/**
	 * Obtiene todas las mesas del Acta Volante para un ciclo Lectivo y Llamado específico. Este método es utilizado por obtenerActaVolanteLlamadoDTO(Integer cicloLectivo)
	 * @param cicloLectivo
	 * @param nombreLlamado
	 * @return
	 * @throws ValidacionException
	 * @throws Exception
	 */
	private List<MenuActaVolanteMesaDTO> obtenerVolanteMesaDTO(Integer cicloLectivo, String nombreLlamado) throws ValidacionException, Exception{
		List<ActaVolanteExamenes> listaActaVolante = new ArrayList<ActaVolanteExamenes>();
		Set<String> mesas = new HashSet<String>(); // Auxiliar Set
		List<MenuActaVolanteMesaDTO> listaMesaActaVolante = new ArrayList<MenuActaVolanteMesaDTO> ();
		listaActaVolante = gActaVolanteExamenes.getByExample(new ActaVolanteExamenes(null,null,nombreLlamado,null,null,null,null,null,null,null,null,null, cicloLectivo,null,true,null)); //Localiza las actas volantes para un ciclo lectivo
		for(ActaVolanteExamenes actaVolante : listaActaVolante){
			if(mesas.add(actaVolante.getNombreMesa())){
				listaMesaActaVolante.add(new MenuActaVolanteMesaDTO(actaVolante.getNombreMesa(),actaVolante.getIdActaVolanteExamen()));
			}
		}
		return listaMesaActaVolante;
	}
	
	/**
	 * Agrega un elemento DetalleVolante al ActaVolanteExamenes según llamado y mesa, una vez finalizada la inscripción del alumno.
	 * @param idMesa
	 * @param idLlamado
	 * @param idAlumno
	 * @throws ValidacionException
	 * @throws Exception
	 */
	private void agregarDetalleDeActaVolante(Long idMesa, Long idLlamado, Long idAlumno) throws ValidacionException, Exception{
		try{
			ActaVolanteExamenes actaVolante = new ActaVolanteExamenes();
			DetalleVolante detalleVolante = new DetalleVolante();
			actaVolante = localizarActaVolanteExamen(idLlamado, idMesa);
			detalleVolante.setAlumno((Alumno)gAlumno.getById(idAlumno));
			detalleVolante.setAsistencia(false);
			detalleVolante.setNota(null);
			gDetalleVolante.add(detalleVolante);
			actaVolante.getDetalles().add(detalleVolante);
			gActaVolanteExamenes.modify(actaVolante);
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * Localiza, elimina un item de Detalle del Acta Volante de Examen y modifica la relación ActaVolante - Detalle.
	 * @param idMesa
	 * @param idLlamado
	 * @param idAlumno
	 * @throws ValidacionException
	 * @throws Exception
	 */
	private void quitarDetalleDeActaVolante(Long idMesa, Long idLlamado, Long idAlumno) throws ValidacionException, Exception{
		try{
			ActaVolanteExamenes actaVolante = new ActaVolanteExamenes();
			Set<DetalleVolante> detallesAux = new HashSet<DetalleVolante>();
			Long idDetalle = null;
			actaVolante = localizarActaVolanteExamen(idLlamado, idMesa);
			detallesAux = actaVolante.getDetalles();
			for(DetalleVolante d : detallesAux){
				if(d.getAlumno().equals(gAlumno.getById(idAlumno))){
					detallesAux.remove(d);
					idDetalle = d.getIdDetalleVolante();
					break;
				}
			}
			if(idDetalle == null){
				ValidacionException vEx = new ValidacionException();
				vEx.addMensajeError("No se pudo eliminar el detalle del Acta Volante de Examen.");
				throw vEx;
			}else{
				gDetalleVolante.delete(gDetalleVolante.getById(idDetalle));
				actaVolante.setDetalles(null);
				actaVolante.setDetalles(detallesAux);
				gActaVolanteExamenes.modify(actaVolante);
			}
			gActaVolanteExamenes.modify(actaVolante);
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	//validarCambios(detallesVolante) // Comprueba que se hayan insertado notas y su valor sea coherente
	//cancelarHistoricosActaVolante(idActaVolante, idDetalle);
	//generarActaVolanteExamenPreexistente(actaVolanteExamen); //El acta volante (necesito la cabecera)
	//volcarDatosAHistoricos(actaVolanteExamen) 
	/**
	 * Determina si se han ingresados valores válidos a asentar en MesaExamenHist. Se ignoran notas en null y se rechazan valores negativos de notas.
	 * @param detalle
	 * @return
	 * @throws Exception
	 */
	private Boolean validarCambios (List<DetalleActaVolanteDTO> detalles) throws ValidacionException, Exception{
		Boolean cambios = false;
		try{
			ValidacionException vEx = new ValidacionException();
			for (DetalleActaVolanteDTO det: detalles){
				if (det.getNota()!= null){
					if (det.getNota()<1){
						vEx.addMensajeError("Existen uno o más valores negativos de notas. Operación rechazada.");
						throw vEx;
					}
					cambios = true;
				}
			}
		}catch(ValidacionException vEx){
			throw vEx;
		}
		return cambios;
	}  
	
	/**
	 * Cancela todos los registros encontrados para el identificador de Acta Volante de Examen e identificador de Detalle. 
	 * @param idActaVolante
	 * @param idDetalle
	 * @throws ValidacionException
	 * @throws Exception
	 */
	private void cancelarHistoricosActaVolante(Long idActaVolante) throws Exception{
		try{
			ActaVolanteExamenes actaVolante = gActaVolanteExamenes.getById(idActaVolante);
			Set<DetalleVolante> detalles = actaVolante.getDetalles(); 
			for (DetalleVolante idDetalle : detalles){
				List<MesaExamenHist> mesaExamenHist = new ArrayList<MesaExamenHist>();
				mesaExamenHist=gMEHist.getByExample(new MesaExamenHist(null, idActaVolante, idDetalle.getIdDetalleVolante(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, true));
				for (MesaExamenHist mesaHist : mesaExamenHist){
					//MesaExamenHist mesaHistAux = new MesaExamenHist();
					//mesaHistAux = mesaHist; 	//Copia para evitar Unmodificable random collection...
					mesaHist.setEstado(false);
					//mesaHistAux.setEstado(false);
					gMEHist.modify(mesaHist);
				}
			}
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * Genera un nuevo actaVolante teniendo en cuenta las modificaciones realizadas de detalles y sin validar existencia de mesa y llamado. Se trata de una réplica de la entidad previamente persistente.
	 * @param actaVolanteExamen
	 * @throws Exception
	 */
	private ActaVolanteExamenes generarActaVolanteExamenPreexistente(ActaVolanteExamenes actaVolanteExamen, List<DetalleActaVolanteDTO> detalles) throws Exception{
		try{
			//Persisitir el acta
			ActaVolanteExamenes actaAux = new ActaVolanteExamenes();
			actaAux.setAnio(actaVolanteExamen.getAnio());
			actaAux.setCicloLectivo(actaVolanteExamen.getCicloLectivo());
			actaAux.setFechaMesa(actaVolanteExamen.getFechaMesa());
			actaAux.setHoraFin(actaVolanteExamen.getHoraFin());
			actaAux.setHoraInicio(actaVolanteExamen.getHoraInicio());
			actaAux.setIdLlamado(actaVolanteExamen.getIdLlamado());
			actaAux.setIdMesa(actaVolanteExamen.getIdMesa());
			actaAux.setNombreLlamado(actaVolanteExamen.getNombreLlamado());
			actaAux.setNombreMesa(actaVolanteExamen.getNombreMesa());
			actaAux.setTribunal1(actaVolanteExamen.getTribunal1());
			actaAux.setTribunal2(actaVolanteExamen.getTribunal2());
			actaAux.setTribunal3(actaVolanteExamen.getTribunal3());
			actaAux.setIdActaVolanteExamen(null);
			gActaVolanteExamenes.add(actaAux);
			Set<DetalleVolante> nuevosDetalles = new HashSet<DetalleVolante>();
			//Persistir los detalles y asociar al nuevo acta.
			for (DetalleActaVolanteDTO det : detalles){
				DetalleVolante detallePreexistente = gDetalleVolante.getById(det.getIdDetalleVolante());
				DetalleVolante nuevoDetalle = new DetalleVolante();
				nuevoDetalle.setAlumno(detallePreexistente.getAlumno());
				nuevoDetalle.setIdDetalleVolante(null);
				nuevoDetalle.setAsistencia(det.getAsistencia());
				nuevoDetalle.setNota(det.getNota());
				gDetalleVolante.add(nuevoDetalle);
				nuevosDetalles.add(nuevoDetalle);
				gDetalleVolante.deleteById(det.getIdDetalleVolante()); // Elimina los detalles ya viejos
			}
			//Asociar y modificar el nuevo ActaVolante
			actaAux.setDetalles(nuevosDetalles);
			actaAux.setModificable(false);
			actaAux.setEstado(true);
			gActaVolanteExamenes.modify(actaAux);
			return actaAux;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * Escribir registros en MesaExamenHist de acuerdo a la información disponible en ActaVolante y Detalle
	 * @param actaVolanteExamen
	 * @throws Exception
	 */
	private void volcarDatosAHistoricos(ActaVolanteExamenes actaVolanteExamen) throws Exception{
		try{
			for (DetalleVolante detalle : actaVolanteExamen.getDetalles() ){
				MesaExamenHist registroHistorico = new MesaExamenHist();
				registroHistorico.setIdActaVolanteExamen(actaVolanteExamen.getIdActaVolanteExamen());
				registroHistorico.setIdDetalleActaVolante(detalle.getIdDetalleVolante());
				registroHistorico.setAnio(actaVolanteExamen.getAnio());
				//registroHistorico.setUsuarioModificador("sa");
				registroHistorico.setCicloLectivoMateria(actaVolanteExamen.getCicloLectivo());
				registroHistorico.setApellidoAlumno(detalle.getAlumno().getApellido());
				registroHistorico.setNombreAlumno(detalle.getAlumno().getNombre());
				registroHistorico.setDniAlumno(detalle.getAlumno().getNroDocumento());
				registroHistorico.setNota(detalle.getNota());
				registroHistorico.setAsistencia(detalle.getAsistencia());
				registroHistorico.setDniDocente1(actaVolanteExamen.getTribunal1().getNroDocumento());
				registroHistorico.setApellidoDocente1(actaVolanteExamen.getTribunal1().getApellido());
				registroHistorico.setNombreDocente1(actaVolanteExamen.getTribunal1().getNombre());
				registroHistorico.setDniDocente2(actaVolanteExamen.getTribunal2().getNroDocumento());
				registroHistorico.setApellidoDocente2(actaVolanteExamen.getTribunal2().getApellido());
				registroHistorico.setNombreDocente2(actaVolanteExamen.getTribunal2().getNombre());
				registroHistorico.setDniDocente3(actaVolanteExamen.getTribunal3().getNroDocumento());
				registroHistorico.setApellidoDocente3(actaVolanteExamen.getTribunal3().getApellido());
				registroHistorico.setNombreDocente3(actaVolanteExamen.getTribunal3().getNombre());
				registroHistorico.setNombreMateria(actaVolanteExamen.getNombreMesa());
				registroHistorico.setFechaHoraInicioMesa(actaVolanteExamen.getFechaMesa());
				registroHistorico.setFechaHoraFinMesa(actaVolanteExamen.getHoraFin());
				registroHistorico.setEstado(true);
				gMEHist.add(registroHistorico);
			}
		}catch(Exception ex){
			throw ex;
		}
	} 
	
}
