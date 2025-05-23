package pua.net.controlador;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

import pua.hibernate.Pua;
import pua.net.dao.DAOException;
import pua.net.dao.DAOFactory;
import pua.net.dao.PuaDAO;
import pua.net.dao.PuaVersionDAO;
import pua.net.dto.*;
import pua.net.modelo.ExportarPDF;


public class PuaAction extends ActionSupport implements ServletContextAware, SessionAware{
	private Logger log;
	private ServletContext servletContext;
	private FacultadDTO facultadDTO;
	private CarreraDTO carreraDTO;
	private MateriaDTO materiaDTO;
	private DAOFactory daoFactory;
	private PuaDAO puaDAO;
	private PuaDTO puaDTO;
	private SubcompetenciaDTO subcompetenciaDTO;
	private TemaDTO temaDTO;
	private SubtemaDTO subtemaDTO;
	private CriterioDTO criterioDTO;
	private EvidenciaDTO evidenciaDTO;
	private GenericaDTO genericaDTO;
	private EspecificaDTO especificaDTO;
	private ActividadDTO actividadDTO;
	private BibliografiaDTO bibliografiaDTO;
	private LibroDTO libroDTO;
	private AmbienteDTO ambienteDTO;
	private PerfilDTO perfilDTO;
	private MaterialDTO materialDTO;
	private EvaluacionDTO evaluacionDTO;
	private List<EvaluacionDTO> listaEvaluacion;
	private EvaluacionFinalDTO evaluacionFinalDTO;
	private AcademiaDTO academiaDTO;
	private String mensaje;
	private String mensajeR;
	private String tipoAccion;
	private int ponderacionSubcompetencia;
	private List<EvaluacionFinalDTO> listaEvaluacionFinal;
	private List<FacultadDTO> listaFacultad;
	private List<CarreraDTO> listaCarrera;
	private List<MateriaDTO> listaMateria;
	private List<MateriaDTO> listaInfoMateria;
	private List<GenericaDTO> listaGenerica;
	private List<EspecificaDTO>listaEspecifica;
	private List<SubcompetenciaDTO>listaSubcompetencias;
	private List<LibroDTO> listaLibros;
	private List<TemaDTO> listaTemas;
	private List<SubtemaDTO> listaSubtemas;
	private List<CriterioDTO> listaCriterios;
	private List<EvidenciaDTO> listaEvidencias;
	private List<EvidenciaDTO> listaEvidenciasSub;
	private List<GenericaDTO> listaGenericasPua;
	private List<EspecificaDTO> listaEspecificasPua;
	private List<DocenteDTO> listaDirector;
	private List<DocenteDTO> listaSecretarioAcademico;	
	private List<DocenteDTO> listaCoordinador;
	private List<DocenteDTO> listaPresidenteAcademia;
	private List<DocenteDTO> listaSecretarioAcademia;
	private List<DocenteDTO> listaDocenteFacultad;
	private List<DocenteDTO> listaDocentePua;
	private List<ActividadDTO> listaActividadesAlumno;
	private List<ActividadDTO> listaActividadesDocente;
	private List<AmbienteDTO> listaAmbientes;
	private List<AmbienteDTO> listaAmbientesSubcompetencia;
	private List<BibliografiaDTO> listaTiposBibliografia;
	private List<LibroDTO> listaBibliografia;
	private List<BibliografiaDTO> listaBibliografiaSub;
	private List<PerfilDTO> listaPerfiles;
	private List<PerfilDTO> listaPerfilesProfesionales;
	private List<PerfilDTO> listaPerfilesDocentes;
	private List<MaterialDTO> listaMateriales;
	private List<MaterialDTO> listaMaterialesSub;
	private List<EvaluacionDTO> listaEvaluacionCompetencias;
	private DocenteDTO docenteDTO;
	private int idFacultad;
	private int idCarrera;
	private int idMateria;
	private int idPua;
	private int idSubcompetencia;
	private int idTipo;
	private String tipo;
	private String facultad;
	private String isCreator;
	
	public String getIsCreator() {
		return isCreator;
	}

	public void setIsCreator(String isCreator) {
		this.isCreator = isCreator;
	}

	public PuaAction() {
		log = LoggerFactory.getLogger(PuaAction.class);
	}
	
	public void setSession(Map<String, Object> arg0) {}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public String execute() {

		String resultado = "";
		HttpSession session = ServletActionContext.getRequest().getSession(true);
		try {
			
			daoFactory = DAOFactory.getDAOFactory(
					Integer.parseInt( servletContext.getInitParameter("TIPO") ), 
					(String) servletContext.getInitParameter("DRIVER"), 
					(String) servletContext.getInitParameter("IP"), 
					(String) servletContext.getInitParameter("BD"), 
					(String) servletContext.getInitParameter("USUARIO"), 
					(String) servletContext.getInitParameter("PASS"));
			puaDAO = daoFactory.getPuaDAO();
			
			if(log.isDebugEnabled()){
				log.debug("Accion: " + tipoAccion);
			}
			
			if(tipoAccion==null || tipoAccion.isEmpty()){
				tipoAccion = "desplegar";
			}
			
			docenteDTO = (DocenteDTO) session.getAttribute("docenteDTO");
			isCreator=(String)session.getAttribute("isCreator");
			session.setAttribute("isCreator", "no");
			if(docenteDTO != null) {
				if(tipo == null || tipo.isEmpty()){
					tipo = "docente";
				}
				
				switch(tipo){
					case "docente":
						idTipo = 6;
						break;
					case "administracion":
						idTipo = docenteDTO.getIdTipo();
						
						break;
				}
				switch(tipoAccion) {
					case "navbar":
						resultado="navbar";
					break;
					case "desplegar":
							session.setAttribute("isCreator", "yes");
							isCreator="yes";
						resultado = "desplegar";
						if(idTipo == 0) {
							listaFacultad=puaDAO.obtenerListaFacultad();
						} else {
							listaFacultad = puaDAO.obtenerDocenteFacultadId(docenteDTO.getIdDocente(), idTipo);
						}
					break;
					case "obtenerFacultades":
						if(idTipo == 0) {
							listaFacultad=puaDAO.obtenerListaFacultad();
						} else {
							listaFacultad = puaDAO.obtenerDocenteFacultadId(docenteDTO.getIdDocente(), idTipo);
						}
						System.out.println("listaFacultades");
						for(int i=0;i<listaFacultad.size();i++)
							System.out.println(listaFacultad.get(i).getFacultad());
						resultado="facultades";
						break;
					case "carreras":
						resultado = "carreras";
						if(idTipo==3){
							listaCarrera = puaDAO.obtenerCarreraCoordinador(docenteDTO.getIdDocente());
						} else if(idTipo == 4 || idTipo == 5){
							listaCarrera=puaDAO.obtenerCarreraMateria(idFacultad, docenteDTO.getIdDocente(),"academia");
						} else if(idTipo == 6){
							listaCarrera=puaDAO.obtenerCarreraMateria(idFacultad, docenteDTO.getIdDocente(),"docente");
						} else {
							listaCarrera = puaDAO.obtenerListaCarreras(idFacultad);
							
						}
						System.out.println("idFacultad" + idFacultad);
						System.out.println("listaCArreras");
						for(int i=0;i<listaCarrera.size();i++)
							System.out.println(listaCarrera.get(i).getNombreCarrera());
						
					break;
					case "materias":
						resultado = "materias";
						if(idTipo == 4 || idTipo == 5) {
							listaMateria=puaDAO.obtenerMateriaAcademia(docenteDTO.getIdDocente(), idCarrera);
						} else if(idTipo == 6) {
							listaMateria = puaDAO.obtenerListaMateriaDocente(docenteDTO.getIdDocente(), idCarrera);
						} else  {
							listaMateria = puaDAO.obtenerListaMateria(idCarrera);
						}
					break;
					case "infoPua":
						resultado = "infoPua";
						
						puaDTO = puaDAO.obtenerPuaPorIdMateria(idMateria);
						idPua = puaDTO.getIdPua();
						carreraDTO = puaDAO.obtenerCarreraPorID(idCarrera);
						if(puaDTO.getIdPua() == 0) {
							puaDTO.setIdMateria(idMateria);
							puaDTO.setPlanEstudio(carreraDTO.getPlanEstudios());
							puaDTO.setIdPua(puaDAO.insertarPua(puaDTO));
							
						}
						
						listaGenerica = puaDAO.obtenerListaCompetenciasGenericas();
						listaEspecifica = puaDAO.obtenerListaCompetenciasEspecificas(idCarrera);					
						materiaDTO = puaDAO.obtenerInfoMateria(idMateria);
						
						
						listaLibros = puaDAO.obtenerListaLibros();
						listaTiposBibliografia = puaDAO.obtenerTiposBibliografia();
						listaDirector = puaDAO.obtenerDirector(idFacultad);
						listaSecretarioAcademico = puaDAO.obtenerSecretarioAcademico(idFacultad);
						listaCoordinador = puaDAO.obtenerCoordinador(idCarrera);
						listaPresidenteAcademia = puaDAO.obtenerPresidenteAcademia(idMateria);
						listaSecretarioAcademia = puaDAO.obtenerSecretarioAcademia(idMateria);
						listaDocenteFacultad = puaDAO.obtenerDocenteFacultad(idFacultad);
						listaEvaluacion = puaDAO.obtenerListaDeEvaluacionCompetencias(idPua);
						listaEvaluacionFinal = puaDAO.obtenerListaDeEvaluacionFinal(idPua);
					break;
					
					case "finalizarPua":
						PuaVersionDAO puaVersionDAO=DAOFactory.getDAOFactory().getPuaVersionDAO();
						Pua aux=puaVersionDAO.getPua(idPua);
						aux.setEstado(false);
						puaVersionDAO.update(aux);
						
						aux.setAutorizado(false);
						aux.setEstado(true);
						aux.setIdPua(puaVersionDAO.save(aux));
						puaVersionDAO.updateSubcompetencia(aux, idPua);
						puaVersionDAO.updateEvaluacion(aux, idPua);
						
						listaEspecificasPua = puaDAO.obtenerEspecificasPua(idPua);
						listaGenericasPua = puaDAO.obtenerGenericasPua(idPua);
						listaDocentePua = puaDAO.obtenerDocentePua(idPua);
						for (EspecificaDTO especificaDTO : listaEspecificasPua) {
							especificaDTO.setIdPua(aux.getIdPua());
							puaDAO.insertarCompetenciaEspecificaPua(especificaDTO);
						}
						
						
						for (GenericaDTO genericaDTO : listaGenericasPua) {
							genericaDTO.setIdPua(aux.getIdPua());
							puaDAO.insertarCompetenciaGenericaPua(genericaDTO);
						}
						resultado="desplegar";
						
						for (DocenteDTO docenteDTO : listaDocentePua) {
							docenteDTO.setIdPua(aux.getIdPua());
							puaDAO.insertarDocentePua(docenteDTO);
						}
						
					resultado = "desplegar";
					if(idTipo == 0) {
					} else {
						listaFacultad = puaDAO.obtenerDocenteFacultadId(docenteDTO.getIdDocente(), idTipo);
						session.setAttribute("isCreator", "yes");
						isCreator="yes";
					}
						break;
					
					case "imprimirPUA":
						resultado = "imprimirPUA";
						
						if(log.isDebugEnabled()){
							log.debug("facultad: " + facultad);
						}
						
						puaDTO = puaDAO.obtenerPuaPorIdMateria(idMateria);
						idPua = puaDTO.getIdPua();
						
						
						  if(puaDTO.getIdPua() == 0 ) {
		                        puaDTO.setIdMateria(idMateria);
		                        int idPua = puaDAO.insertarPua(puaDTO);
		                        puaDTO.setIdPua(idPua);
		                    }
						  	
		                    carreraDTO = puaDAO.obtenerCarreraPorID(idCarrera);
						    facultadDTO = puaDAO.obtenerFacultadPorID(idFacultad);
		                    listaEspecificasPua = puaDAO.obtenerEspecificasPua(idPua);
		                    materiaDTO = puaDAO.obtenerInfoMateria(idMateria);
		                    listaGenericasPua = puaDAO.obtenerGenericasPua(idPua);
		                    listaBibliografia = puaDAO.obtenerBibliografiaPua(idPua);
		                    listaPerfiles = puaDAO.obtenerPerfilesAcademicos(idPua);
		                    listaPerfilesProfesionales = puaDAO.obtenerPerfilesProfesional(idPua);
		                    listaPerfilesDocentes = puaDAO.obtenerPerfilesDocente(idPua);
		                    listaDocentePua = puaDAO.obtenerDocentePua(idPua);
		                    listaPresidenteAcademia = puaDAO.obtenerPresidenteAcademia(idMateria);
		                    listaSecretarioAcademia = puaDAO.obtenerSecretarioAcademia(idMateria);
		                    listaSubcompetencias = puaDAO.obtenerSubcompetencias(idPua);
		                    listaDirector = puaDAO.obtenerDirector(idFacultad);
		                    listaSecretarioAcademico = puaDAO.obtenerSecretarioAcademico(idFacultad);
							listaCoordinador = puaDAO.obtenerCoordinador(idCarrera);
							listaEvaluacion = puaDAO.obtenerListaDeEvaluacionCompetencias(idPua);
							listaEvaluacionFinal = puaDAO.obtenerListaDeEvaluacionFinal(idPua);
							academiaDTO = puaDAO.obtenerAcademia(idMateria);
							puaDTO.setAcademiaDTO(academiaDTO);
							puaDTO.setCarreraDTO(carreraDTO);
							puaDTO.setMateriaDTO(materiaDTO);
							puaDTO.setFacultadDTO(facultadDTO);
							
							List<PerfilDTO> listaPerfilesAux=new ArrayList<PerfilDTO>();
							PerfilDTO perfil=new PerfilDTO();
							perfil.setListaAcademicos(listaPerfiles);
							perfil.setListaDocentes(listaPerfilesDocentes);
							perfil.setListaProfesionales(listaPerfilesProfesionales);
							listaPerfilesAux.add(perfil);
							new ExportarPDF().exportToPDF(listaGenericasPua, listaEspecificasPua, puaDTO,listaSubcompetencias, listaBibliografia,
									listaEvaluacionFinal,listaEvaluacion,listaPerfilesAux,listaDocentePua,listaPresidenteAcademia,listaSecretarioAcademia,
									listaCoordinador,listaSecretarioAcademico,listaDirector,"");
					break;
					
				
					
					case "infoSubcompetencias":
						resultado = "infoSubcompetencias";
						
						listaSubcompetencias = puaDAO.obtenerSubcompetencias(idPua);
						listaLibros = puaDAO.obtenerListaLibros();
						listaAmbientes = puaDAO.obtenerListaAmbientes();
						listaBibliografia = puaDAO.obtenerBibliografiaPua(idPua);
						listaMateriales = puaDAO.obtenerListaMateriales();
					break;
					
					case "obtenerGenericasPua":
						resultado = "obtenerGenericasPua";
						
						listaGenericasPua = puaDAO.obtenerGenericasPua(idPua);
					break;
					
					case "insertarCompetenciaGenericaPua":
						resultado = "insertarCompetenciaGenericaPua";
						
						puaDAO.insertarCompetenciaGenericaPua(genericaDTO);
						
						mensajeR = "exito";
					break;
					
					case "eliminarCompetenciaGenericaPua":
						resultado = "eliminarCompetenciaGenericaPua";
						
						puaDAO.eliminarCompetenciaGenerica(genericaDTO);
						mensajeR = "exito";
					break;
					
					case "eliminarEvaluacionCompetenicias":
						resultado = "eliminarEvaluacionCompetenicias";					
						puaDAO.eliminarEvaluacionCompetencias(evaluacionDTO);
					
					break;
					
					case "eliminarEvaluacionFinal":
						resultado = "eliminarEvaluacionFinal";					
						puaDAO.eliminarEvaluacionFinal(evaluacionFinalDTO);
					
					break;
					
					case "obtenerEspecificasPua":
						resultado = "obtenerEspecificasPua";
						
						listaEspecificasPua = puaDAO.obtenerEspecificasPua(idPua);
					break;
	
					case "insertarCompetenciaEspecificaPua":
						resultado = "insertarCompetenciaEspecificaPua";
						
						puaDAO.insertarCompetenciaEspecificaPua(especificaDTO);
						
						mensajeR = "exito";
					break;
					
					case "eliminarCompetenciaEspecificaPua":
						resultado = "eliminarCompetenciaEspecificaPua";
						
						puaDAO.eliminarCompetenciaEspecifica(especificaDTO);
						
						mensajeR = "exito";
					break;
					
					case "guardarCompetenciasPua":
						resultado = "guardarCompetenciasPua";
						
						puaDAO.guardarPua(puaDTO);	
						mensajeR = "exito";
					break;
	
					case "generarSubcompetencia":
						resultado = "generarSubcompetencia";
		
						idSubcompetencia = puaDAO.insertarSubcompetencia(subcompetenciaDTO);
						subcompetenciaDTO.setIdSubcompetencia(idSubcompetencia);				
		
						mensajeR = "exito";
					break;
					
					case "insertarEvaluacionFinal":
						resultado = "insertarEvaluacionFinal";
						
						puaDAO.insertarEvaluacionFinal(evaluacionDTO);
						mensajeR = "exito";
					break;
						
					case "insertarEvaluacionCompetencias":
						resultado = "insertarEvaluacionCompetencias";
						
						puaDAO.insertarEvaluacionCompetencias(evaluacionDTO);
						mensajeR = "exito";
					break;
					
					case "obtenerEvaluacionCompetencias":
						resultado = "obtenerEvaluacionCompetencias";
						listaEvaluacion = puaDAO.obtenerListaDeEvaluacionCompetencias(idPua);	
					break;
					
					case "obtenerEvaluacionFinal":
						resultado = "obtenerEvaluacionFinal";
						listaEvaluacionFinal = puaDAO.obtenerListaDeEvaluacionFinal(idPua);	
					break;
					
					case "guardarSubcompetencia":
						
						int pondParc = puaDAO.obtenerPonderacionExcepto(idPua, subcompetenciaDTO.getIdSubcompetencia());
											
						if ((pondParc + subcompetenciaDTO.getPonderacion()) > 100) {
							throw new DAOException("La suma de las ponderaciones ha sobrepasado el 100%");
							
						} else {
							resultado = "guardarSubcompetencia";
							puaDAO.guardarSubcompetencia(subcompetenciaDTO);
							if(log.isDebugEnabled()){
								log.debug("idSubcompetencia: " + subcompetenciaDTO.getIdSubcompetencia());
								log.debug("Sesiones: " + subcompetenciaDTO.getSesiones());
								log.debug("Ponderacion: " + subcompetenciaDTO.getPonderacion());
								log.debug("Parcial: " + subcompetenciaDTO.getParcial());
							}
							mensajeR = "exito";
						}
						
					break;
					
					case "eliminarSubcompetencia":
						resultado = "eliminarSubcompetencia";
						puaDAO.eliminarSubcompetencia(idSubcompetencia);
					break;
					
					case "obtenerTemas":
						resultado = "obtenerTemas";
		
						listaTemas = puaDAO.obtenerTemas(idSubcompetencia);	
					break;
					
					case "insertarTema":
						resultado = "insertarTema";
						
						puaDAO.insertarTema(temaDTO);
						
						mensajeR = "exito";
					break;
					
					case "eliminarTema":
						resultado = "eliminarTema";
						
						puaDAO.eliminarTema(temaDTO.getIdTema());
						
						mensajeR = "exito";
					break;
					
					case "obtenerSubtemas":
						resultado = "obtenerSubtemas";
						
						listaSubtemas = puaDAO.obtenerSubtemas(temaDTO.getIdTema());
					break;
					
					case "insertarSubtema":
						resultado = "insertarSubtema";
						
						puaDAO.insertarSubtema(subtemaDTO);
						
						mensajeR = "exito";
					break;
					
					case "eliminarSubtema":
						resultado = "eliminarSubtema";
		
						puaDAO.eliminarSubtema(subtemaDTO.getIdSubtema());
						
						mensajeR = "exito";
					break;
					
					case "obtenerCriterios":
						resultado = "obtenerCriterios";
						
						listaCriterios = puaDAO.obtenerCriterios(idSubcompetencia);
					break;
					
					case "insertarCriterio":
						resultado = "insertarCriterio";
						
						puaDAO.insertarCriterio(criterioDTO);
						
						mensajeR = "exito";
					break;
					
					case "eliminarCriterio":
						resultado = "eliminarCriterio";
						
						puaDAO.eliminarCriterio(criterioDTO.getIdCriterio());
						mensajeR = "exito";
					break;
					
					case "obtenerEvidencias":
						resultado = "obtenerEvidencias";
						listaEvidencias = puaDAO.obtenerEvidencias();
						
						listaEvidenciasSub = puaDAO.obtenerEvidenciasSubcompetencia(idSubcompetencia);
						
					break;
					
					case "insertarEvidencia":
						resultado = "insertarEvidencia";
						
						int pondTotal = puaDAO.obtenerPonderacionEvidencias(evidenciaDTO.getIdSubcompetencia());
						int pondEntrante = evidenciaDTO.getPonderacion();
						
						if((pondTotal + pondEntrante) > 100) {
							
							throw new DAOException("Ha sobrepasado el 100% de la ponderación total");
						
						} else {
							resultado = "insertarEvidencia";
							puaDAO.insertarEvidencia(evidenciaDTO);
							mensajeR = "exito";
						}
	
					break;
					
					case "eliminarEvidencia":
						resultado = "eliminarEvidencia";
						
						puaDAO.eliminarEvidencia(evidenciaDTO);
						
						mensajeR = "exito";
					break;
					
					case "obtenerDocentePua":
						resultado = "obtenerDocentePua";
						
						listaDocentePua = puaDAO.obtenerDocentePua(idPua);
					break;
					
					case "insertarDocentePua":
						resultado = "insertarDocentePua";
						docenteDTO=new DocenteDTO();
						docenteDTO.setIdPua(idPua);
						docenteDTO.setIdDocente(Integer.parseInt(mensaje));
						puaDAO.insertarDocentePua(docenteDTO);
						
						mensajeR = "exito";
					break;
					
					case "eliminarDocentePua":
						resultado = "eliminarDocentePua";
						docenteDTO=new DocenteDTO();
						docenteDTO.setIdPua(idPua);
						docenteDTO.setIdDocente(Integer.parseInt(mensaje));
						puaDAO.eliminarDocentePua(docenteDTO);
					break;
					
					case "obtenerActividades":
						resultado = "obtenerActividades";
						
						listaActividadesAlumno = puaDAO.obtenerActividadesAlumno(idSubcompetencia);
						listaActividadesDocente = puaDAO.obtenerActividadesDocente(idSubcompetencia);
					break;
					
					case "insertarActividad":
						resultado = "insertarActividad";
						
						if(actividadDTO.getRolDeActividad().equals("Alumno")) {
							puaDAO.insertarActividadAlumno(actividadDTO);
							
						} else if(actividadDTO.getRolDeActividad().equals("Docente")) {
							puaDAO.insertarActividadDocente(actividadDTO);
						} else {
							mensajeR = "exito";
						}
						
					break;
					
					case "eliminarActividad":
						resultado = "eliminarActividad";
						
						String rol = actividadDTO.getRolDeActividad();
						
						if(rol.equals("Alumno")) {
							puaDAO.eliminarActividadAlumno(actividadDTO.getIdActividad());
							
						} else if(rol.equals("Docente")) {
							puaDAO.eliminarActividadDocente(actividadDTO.getIdActividad());
						}
					
						mensajeR = "exito";
					break;
					
					case "obtenerBibliografiaPua":
						resultado = "obtenerBibliografiaPua";
						
						listaBibliografia = puaDAO.obtenerBibliografiaPua(idPua);
					break;
	
					case "insertarBibliografiaPua":
						resultado = "insertarBibliografiaPua";
						
						puaDAO.insertarBibliografiaPua(bibliografiaDTO);
						mensajeR = "exito";
					break;
	
					case "eliminarBibliografiaPua":
						resultado = "eliminarBibliografiaPua";
						
						puaDAO.eliminarBibliografiaPua(bibliografiaDTO);
						
						mensajeR = "exito";
					break;
					
					case "obtenerAmbientesSubcompetencia":
						
						resultado = "obtenerAmbientesSubcompetencia";
						listaAmbientesSubcompetencia = puaDAO.obtenerAmbientesSubcompetencia(idSubcompetencia);
						
					break;
					
					case "insertarAmbienteSubcompetencia":
						resultado = "insertarAmbienteSubcompetencia";
						
						puaDAO.insertarAmbienteSubcompetencia(ambienteDTO);
						
						mensajeR = "exito";
					break;
					
					case "eliminarAmbiente":
						resultado = "eliminarAmbiente";
					
						puaDAO.eliminarAmbiente(ambienteDTO);
						
						mensajeR = "exito";
					break;
					
					case "obtenerBibliografiaSubcompetencia":
						resultado = "obtenerBibliografiaSubcompetencia";
						
						listaBibliografiaSub = puaDAO.obtenerBibliografiaSubcompetencia(idSubcompetencia);
					
					break;
					
					case "insertarBibliografiaSubcompetencia":
						resultado = "insertarBibliografiaSubcompetencia";
						
						puaDAO.insertarBibliografiaSubcompetencia(bibliografiaDTO);
						
						mensajeR = "exito";
					break;
	
					case "eliminarBibliografiaSubcompetencia":
						resultado = "eliminarBibliografiaSubcompetencia";
						
						puaDAO.eliminarBibliografiaSubcompetencia(bibliografiaDTO);
						
						mensajeR = "exito";
					break;
					
					case "obtenerPerfilAcademico":
						resultado = "obtenerPerfilAcademico";
						listaPerfiles = puaDAO.obtenerPerfilesAcademicos(idPua);
						
					break;
					
					case "obtenerPerfilProfesional":
						resultado = "obtenerPerfilProfesional";
						listaPerfiles = puaDAO.obtenerPerfilesProfesional(idPua);
						
					break;
					
					case "obtenerPerfilDocente":
						resultado = "obtenerPerfilDocente";
						listaPerfiles = puaDAO.obtenerPerfilesDocente(idPua);
						
					break;
					
					case "insertarPerfilAcademico":
						resultado = "insertarPerfilAcademico";
						
						puaDAO.insertarPerfilAcademico(perfilDTO);
						
						mensajeR = "exito";
					break;
					
					case "insertarPerfilProfesional":
						resultado = "insertarPerfilProfesional";
						
						puaDAO.insertarPerfilProfesional(perfilDTO);
						
						mensajeR = "exito";
					break;
					
					case "insertarPerfilDocente":
						resultado = "insertarPerfilDocente";
						
						puaDAO.insertarPerfilDocente(perfilDTO);
						
						mensajeR = "exito";
					break;
					
					case "eliminarPerfilAcad":
						resultado = "eliminarPerfilAcad";
						
						puaDAO.eliminarPerfilAcademico(perfilDTO.getIdPerfil());
						mensajeR = "exito";
					break;
					
					case "eliminarPerfilProf":
						resultado = "eliminarPerfilProf";
						
						puaDAO.eliminarPerfilProfesional(perfilDTO.getIdPerfil());
						mensajeR = "exito";
					break;
					
					case "eliminarPerfilDoc":
						resultado = "eliminarPerfilDoc";
						
						puaDAO.eliminarPerfilDocente(perfilDTO.getIdPerfil());
						mensajeR = "exito";
					break;
					
					case "obtenerMaterialesSub":
						resultado = "obtenerMaterialesSub";
						listaMaterialesSub = puaDAO.obtenerListaMaterialesSubcompetencia(idSubcompetencia);
					break;
					
					case "insertarMaterialSub":
						resultado = "insertarMaterialSub";
						
						puaDAO.insertarMaterial(materialDTO);
						
						mensajeR = "exito";
					break;
					
					case "eliminarMaterial":
						resultado = "eliminarMaterial";
						
						puaDAO.eliminarMaterial(materialDTO);
						
						mensajeR = "exito";
					break;
				}
			} else {
				switch(tipoAccion) {
					case "navbar":
						resultado="navbar";
					break;
					case "obtenerFacultades":
						if(idTipo == 0) {
							listaFacultad=puaDAO.obtenerListaFacultad();
						} else {
							listaFacultad = puaDAO.obtenerDocenteFacultadId(docenteDTO.getIdDocente(), idTipo);
						}
						resultado="facultades";
						break;
					case "carreras":
						resultado = "carreras";
						if(idTipo==3){
							listaCarrera = puaDAO.obtenerCarreraCoordinador(docenteDTO.getIdDocente());
						} else if(idTipo == 4 || idTipo == 5){
							listaCarrera=puaDAO.obtenerCarreraMateria(idFacultad, docenteDTO.getIdDocente(),"academia");
						} else if(idTipo == 6){
							listaCarrera=puaDAO.obtenerCarreraMateria(idFacultad, docenteDTO.getIdDocente(),"docente");
						} else {
							listaCarrera = puaDAO.obtenerListaCarreras(idFacultad);
						}
						
					break;
					default:
						resultado="logout";
					break;
				}
				
			}

		} catch(SQLException e) {
			resultado = "error";
			mensaje = "Ocurrio un error de SQLException al ejecutar la función.";
			mensajeR="fallo";
			e.printStackTrace();	
			
		} catch(DAOException e) {
			resultado = "error";
			mensaje = e.getMessage();
			mensajeR = "fallo";
			e.printStackTrace();
		
		} 
		System.out.println("Entrada: " + tipoAccion + ", Salida: " + resultado);
		return resultado;
	}

	public DocenteDTO getDocenteDTO() {
		return docenteDTO;
	}

	public void setDocenteDTO(DocenteDTO docenteDTO) {
		this.docenteDTO = docenteDTO;
	}

	public List<EvaluacionFinalDTO> getListaEvaluacionFinal() {
		return listaEvaluacionFinal;
	}

	public void setListaEvaluacionFinal(List<EvaluacionFinalDTO> listaEvaluacionFinal) {
		this.listaEvaluacionFinal = listaEvaluacionFinal;
	}

	public List<EvaluacionDTO> getListaEvaluacion() {
		return listaEvaluacion;
	}

	public void setListaEvaluacion(List<EvaluacionDTO> listaEvaluacion) {
		this.listaEvaluacion = listaEvaluacion;
	}

	public List<EvidenciaDTO> getListaEvidenciasSub() {
		return listaEvidenciasSub;
	}

	public void setListaEvidenciasSub(List<EvidenciaDTO> listaEvidenciasSub) {
		this.listaEvidenciasSub = listaEvidenciasSub;
	}

	public FacultadDTO getFacultadDTO() {
		return facultadDTO;
	}

	public void setFacultadDTO(FacultadDTO facultadDTO) {
		this.facultadDTO = facultadDTO;
	}
	
	public CarreraDTO getCarreraDTO() {
		return carreraDTO;
	}

	public void setCarreraDTO(CarreraDTO carreraDTO) {
		this.carreraDTO = carreraDTO;
	}
	public MateriaDTO getMateriaDTO() {
		return materiaDTO;
	}

	public void setMateriaDTO(MateriaDTO materiaDTO) {
		this.materiaDTO = materiaDTO;
	}
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getTipoAccion() {
		return tipoAccion;
	}

	public void setTipoAccion(String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	public List<FacultadDTO> getListaFacultad() {
		return listaFacultad;
	}

	public void setListaFacultad(List<FacultadDTO> listaFacultad) {
		this.listaFacultad = listaFacultad;
	}
	public List<CarreraDTO> getListaCarrera() {
		return listaCarrera;
	}

	public void setListaCarrera(List<CarreraDTO> listaCarrera) {
		this.listaCarrera= listaCarrera;
	}
	public List<MateriaDTO> getListaMateria() {
		return listaMateria;
	}

	public void setListaMateria(List<MateriaDTO> listaMateria) {
		this.listaMateria = listaMateria;
	}

	public int getIdCarrera() {
		return idCarrera;
	}

	public void setIdCarrera(int idCarrera) {
		this.idCarrera = idCarrera;
	}
	
	public int getIdFacultad() {
		return idFacultad;
	}

	public void setIdFacultad(int idFacultad) {
		this.idFacultad = idFacultad;
	}

	public int getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(int idMateria) {
		this.idMateria = idMateria;
	}

	public List<MateriaDTO> getListaInfoMateria() {
		return listaInfoMateria;
	}

	public void setListaInfoMateria(List<MateriaDTO> listaInfoMateria) {
		this.listaInfoMateria = listaInfoMateria;
	}

	public List<GenericaDTO> getListaGenerica() {
		return listaGenerica;
	}

	public void setListaGenerica(List<GenericaDTO> listaGenerica) {
		this.listaGenerica = listaGenerica;
	}
	
	public List<EspecificaDTO> getListaEspecifica() {
		return listaEspecifica;
	}

	public void setListaEspecifica(List<EspecificaDTO> listaEspecifica) {
		this.listaEspecifica = listaEspecifica;
	}

	public PuaDTO getPuaDTO() {
		return puaDTO;
	}

	public void setPuaDTO(PuaDTO puaDTO) {
		this.puaDTO = puaDTO;
	}

	public String getMensajeR() {
		return mensajeR;
	}

	public void setMensajeR(String mensajeR) {
		this.mensajeR = mensajeR;
	}

	public SubcompetenciaDTO getSubcompetenciaDTO() {
		return subcompetenciaDTO;
	}

	public void setSubcompetenciaDTO(SubcompetenciaDTO subcompetenciaDTO) {
		this.subcompetenciaDTO = subcompetenciaDTO;
	}

	public int getIdPua() {
		return idPua;
	}

	public void setIdPua(int idPua) {
		this.idPua = idPua;
	}

	public List<SubcompetenciaDTO> getListaSubcompetencias() {
		return listaSubcompetencias;
	}

	public void setListaSubcompetencias(List<SubcompetenciaDTO> listaSubcompetencias) {
		this.listaSubcompetencias = listaSubcompetencias;
	}
	
	//------->
	
	public List<LibroDTO> getListaLibros() {
		return listaLibros;
	}

	public void setListaLibros(List<LibroDTO> listaLibros) {
		this.listaLibros= listaLibros;
	}

	public int getIdSubcompetencia() {
		return idSubcompetencia;
	}

	public void setIdSubcompetencia(int idSubcompetencia) {
		this.idSubcompetencia = idSubcompetencia;
	}

	public List<TemaDTO> getListaTemas() {
		return listaTemas;
	}

	public void setListaTemas(List<TemaDTO> listaTemas) {
		this.listaTemas = listaTemas;
	}

	public TemaDTO getTemaDTO() {
		return temaDTO;
	}

	public void setTemaDTO(TemaDTO temaDTO) {
		this.temaDTO = temaDTO;
	}

	public SubtemaDTO getSubtemaDTO() {
		return subtemaDTO;
	}

	public void setSubtemaDTO(SubtemaDTO subtemaDTO) {
		this.subtemaDTO = subtemaDTO;
	}

	public List<SubtemaDTO> getListaSubtemas() {
		return listaSubtemas;
	}

	public void setListaSubtemas(List<SubtemaDTO> listaSubtemas) {
		this.listaSubtemas = listaSubtemas;
	}
	
	public List<CriterioDTO> getListaCriterios() {
		return listaCriterios;
	}

	public void setListaCriterios(List<CriterioDTO> listaCriterios) {
		this.listaCriterios = listaCriterios;
	}

	public CriterioDTO getCriterioDTO() {
		return criterioDTO;
	}

	public void setCriterioDTO(CriterioDTO criterioDTO) {
		this.criterioDTO = criterioDTO;
	}

	public List<EvidenciaDTO> getListaEvidencias() {
		return listaEvidencias;
	}

	public void setListaEvidencias(List<EvidenciaDTO> listaEvidencias) {
		this.listaEvidencias = listaEvidencias;
	}

	public EvidenciaDTO getEvidenciaDTO() {
		return evidenciaDTO;
	}

	public void setEvidenciaDTO(EvidenciaDTO evidenciaDTO) {
		this.evidenciaDTO = evidenciaDTO;
	}

	public GenericaDTO getGenericaDTO() {
		return genericaDTO;
	}

	public void setGenericaDTO(GenericaDTO genericaDTO) {
		this.genericaDTO = genericaDTO;
	}

	public List<GenericaDTO> getListaGenericasPua() {
		return listaGenericasPua;
	}

	public void setListaGenericasPua(List<GenericaDTO> listaGenericasDePua) {
		this.listaGenericasPua = listaGenericasDePua;
	}

	public EspecificaDTO getEspecificaDTO() {
		return especificaDTO;
	}

	public void setEspecificaDTO(EspecificaDTO especificaDTO) {
		this.especificaDTO = especificaDTO;
	}

	public List<EspecificaDTO> getListaEspecificasPua() {
		return listaEspecificasPua;
	}

	public void setListaEspecificasPua(List<EspecificaDTO> listaEspecificasPua) {
		this.listaEspecificasPua = listaEspecificasPua;
	}

	public List<DocenteDTO> getListaDirector() {
		return listaDirector;
	}

	public void setListaDirector(List<DocenteDTO> listaDirector) {
		this.listaDirector = listaDirector;
	}

	public List<DocenteDTO> getListaSecretarioAcademico() {
		return listaSecretarioAcademico;
	}

	public void setListaSecretarioAcademico(List<DocenteDTO> listaSecretarioAcademico) {
		this.listaSecretarioAcademico = listaSecretarioAcademico;
	}

	public List<DocenteDTO> getListaCoordinador() {
		return listaCoordinador;
	}

	public void setListaCoordinador(List<DocenteDTO> listaCoordinador) {
		this.listaCoordinador = listaCoordinador;
	}

	public List<DocenteDTO> getListaPresidenteAcademia() {
		return listaPresidenteAcademia;
	}

	public void setListaPresidenteAcademia(List<DocenteDTO> listaPresidenteAcademia) {
		this.listaPresidenteAcademia = listaPresidenteAcademia;
	}

	public List<DocenteDTO> getListaSecretarioAcademia() {
		return listaSecretarioAcademia;
	}

	public void setListaSecretarioAcademia(List<DocenteDTO> listaSecretarioAcademia) {
		this.listaSecretarioAcademia = listaSecretarioAcademia;
	}

	public List<DocenteDTO> getListaDocenteFacultad() {
		return listaDocenteFacultad;
	}

	public void setListaDocenteFacultad(List<DocenteDTO> listaDocenteFacultad) {
		this.listaDocenteFacultad = listaDocenteFacultad;
	}

	public List<DocenteDTO> getListaDocentePua() {
		return listaDocentePua;
	}

	public void setListaDocentePua(List<DocenteDTO> listaDocentePua) {
		this.listaDocentePua = listaDocentePua;
	}

	public List<ActividadDTO> getListaActividadesAlumno() {
		return listaActividadesAlumno;
	}

	public void setListaActividadesAlumno(List<ActividadDTO> listaActividadesAlumno) {
		this.listaActividadesAlumno = listaActividadesAlumno;
	}
	
	public List<ActividadDTO> getListaActividadesDocente() {
		return listaActividadesDocente;
	}

	public void setListaActividadesDocente(List<ActividadDTO> listaActividadesDocente) {
		this.listaActividadesDocente = listaActividadesDocente;
	}

	public ActividadDTO getActividadDTO() {
		return actividadDTO;
	}

	public void setActividadDTO(ActividadDTO actividadDTO) {
		this.actividadDTO = actividadDTO;
	}

	public List<AmbienteDTO> getListaAmbientes() {
		return listaAmbientes;
	}

	public void setListaAmbientes(List<AmbienteDTO> listaAmbientes) {
		this.listaAmbientes = listaAmbientes;
	}

	public List<BibliografiaDTO> getListaTiposBibliografia() {
		return listaTiposBibliografia;
	}

	public void setListaTiposBibliografia(List<BibliografiaDTO> listaTiposBibliografia) {
		this.listaTiposBibliografia = listaTiposBibliografia;
	}

	public BibliografiaDTO getBibliografiaDTO() {
		return bibliografiaDTO;
	}

	public void setBibliografiaDTO(BibliografiaDTO bibliografiaDTO) {
		this.bibliografiaDTO = bibliografiaDTO;
	}

	public List<LibroDTO> getListaBibliografia() {
		return listaBibliografia;
	}

	public void setListaBibliografia(List<LibroDTO> listaBibliografia) {
		this.listaBibliografia = listaBibliografia;
	}

	public LibroDTO getLibroDTO() {
		return libroDTO;
	}

	public void setLibroDTO(LibroDTO libroDTO) {
		this.libroDTO = libroDTO;
	}
	
	public AmbienteDTO getAmbienteDTO() {
		return ambienteDTO;
	}

	public void setAmbienteDTO(AmbienteDTO ambienteDTO) {
		this.ambienteDTO = ambienteDTO;
	}

	public List<AmbienteDTO> getListaAmbientesSubcompetencia() {
		return listaAmbientesSubcompetencia;
	}

	public void setListaAmbientesSubcompetencia(List<AmbienteDTO> listaAmbientesSubcompetencia) {
		this.listaAmbientesSubcompetencia = listaAmbientesSubcompetencia;
	}

	public int getPonderacionSubcompetencia() {
		return ponderacionSubcompetencia;
	}

	public void setPonderacionSubcompetencia(int ponderacionSubcompetencia) {
		this.ponderacionSubcompetencia = ponderacionSubcompetencia;
	}

	public List<BibliografiaDTO> getListaBibliografiaSub() {
		return listaBibliografiaSub;
	}

	public void setListaBibliografiaSub(List<BibliografiaDTO> listaBibliografiaSub) {
		this.listaBibliografiaSub = listaBibliografiaSub;
	}

	public List<PerfilDTO> getListaPerfiles() {
		return listaPerfiles;
	}

	public void setListaPerfiles(List<PerfilDTO> listaPerfiles) {
		this.listaPerfiles = listaPerfiles;
	}

	public PerfilDTO getPerfilDTO() {
		return perfilDTO;
	}

	public void setPerfilDTO(PerfilDTO perfilDTO) {
		this.perfilDTO = perfilDTO;
	}

	public List<MaterialDTO> getListaMateriales() {
		return listaMateriales;
	}

	public void setListaMateriales(List<MaterialDTO> listaMateriales) {
		this.listaMateriales = listaMateriales;
	}

	public MaterialDTO getMaterialDTO() {
		return materialDTO;
	}

	public void setMaterialDTO(MaterialDTO materialDTO) {
		this.materialDTO = materialDTO;
	}

	public List<MaterialDTO> getListaMaterialesSub() {
		return listaMaterialesSub;
	}

	public void setListaMaterialesSub(List<MaterialDTO> listaMaterialesSub) {
		this.listaMaterialesSub = listaMaterialesSub;
	}

	public List<PerfilDTO> getListaPerfilesProfesionales() {
		return listaPerfilesProfesionales;
	}

	public void setListaPerfilesProfesionales(List<PerfilDTO> listaPerfilesProfesionales) {
		this.listaPerfilesProfesionales = listaPerfilesProfesionales;
	}

	public List<PerfilDTO> getListaPerfilesDocentes() {
		return listaPerfilesDocentes;
	}

	public void setListaPerfilesDocentes(List<PerfilDTO> listaPerfilesDocentes) {
		this.listaPerfilesDocentes = listaPerfilesDocentes;
	}

	public EvaluacionDTO getEvaluacionDTO() {
		return evaluacionDTO;
	}

	public void setEvaluacionDTO(EvaluacionDTO evaluacionDTO) {
		this.evaluacionDTO = evaluacionDTO;
	}

	public List<EvaluacionDTO> getListaEvaluacionCompetencias() {
		return listaEvaluacionCompetencias;
	}

	public void setListaEvaluacionCompetencias(List<EvaluacionDTO> listaEvaluacionCompetencias) {
		this.listaEvaluacionCompetencias = listaEvaluacionCompetencias;
	}

	public EvaluacionFinalDTO getEvaluacionFinalDTO() {
		return evaluacionFinalDTO;
	}

	public void setEvaluacionFinalDTO(EvaluacionFinalDTO evaluacionFinalDTO) {
		this.evaluacionFinalDTO = evaluacionFinalDTO;
	}

	public AcademiaDTO getAcademiaDTO() {
		return academiaDTO;
	}

	public void setAcademiaDTO(AcademiaDTO academiaDTO) {
		this.academiaDTO = academiaDTO;
	}
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}
}