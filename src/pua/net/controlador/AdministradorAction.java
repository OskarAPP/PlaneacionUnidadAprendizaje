package pua.net.controlador;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

import pua.net.dao.DAOException;
import pua.net.dao.DAOFactory;
import pua.net.dao.mysql.MySQL_Listas;
import pua.net.dao.AdministradorDAO;
import pua.net.dto.AcademiaDTO;
import pua.net.dto.AccesoDTO;
import pua.net.dto.CarreraDTO;
import pua.net.dto.DocenteDTO;
import pua.net.dto.EspecificaDTO;
import pua.net.dto.FacultadDTO;
import pua.net.dto.GenericaDTO;
import pua.net.dto.MateriaDTO;
import pua.net.modelo.RegistroCSV;
import pua.net.pruebas.PruebaCsv;

public class AdministradorAction extends ActionSupport implements ServletContextAware, SessionAware{
	private Logger log;
	private ServletContext servletContext;
	private AdministradorDAO adminDAO;
	private MateriaDTO materiaDTO;
	private FacultadDTO facultadDTO;
	private CarreraDTO carreraDTO;
	private DocenteDTO docenteDTO;
	private AcademiaDTO academiaDTO;
	private GenericaDTO genericaDTO;
	private AccesoDTO accesoDTO;
	private EspecificaDTO especificaDTO;
	private List<GenericaDTO> listaGenerica;
	private List<EspecificaDTO> listaEspecifica;
	private List<MateriaDTO> listaNucleo;
	private List<MateriaDTO> listaArea;
	private List<MateriaDTO> listaTipo;
	private List<MateriaDTO> listaMateria;
	private List<CarreraDTO> listaCarrera;
	private List<FacultadDTO> listaFacultad;
	private List<DocenteDTO> listaTipoDocente;
	private List<AcademiaDTO> listaAcademia;
	private MySQL_Listas listasF;
	private int idFacultad;
	private int idCarrera;
	private int idMateria;
	private int idAcademia;
	private int idCargo;
	private int orden;
	private String nombreDocente;
	private String nombreMateria;
	private String mensaje;
	private String mensajeR;
	private String exito;
	private String alerta;
	private String tipoAccion;
	private String nombreCompetencia;
	private String rolAcceso;
	private boolean isAdministrador;
	private DAOFactory daoFactory;
	private File archivo;
	private SessionMap<String,Object> session;
	
	public AdministradorAction() {
		log = LoggerFactory.getLogger(AdministradorAction.class);
	}
	
	public void setSession(Map<String, Object> arg0) {
		this.session = (SessionMap<String, Object>) session;
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public String execute() {
		String resultado = "";
		try {
			daoFactory = DAOFactory.getDAOFactory(
					Integer.parseInt( servletContext.getInitParameter("TIPO") ), 
					(String) servletContext.getInitParameter("DRIVER"), 
					(String) servletContext.getInitParameter("IP"), 
					(String) servletContext.getInitParameter("BD"), 
					(String) servletContext.getInitParameter("USUARIO"), 
					(String) servletContext.getInitParameter("PASS"));

			adminDAO=daoFactory.getAdministradorDAO();
			listasF=MySQL_Listas.getInstance(adminDAO);
			if(log.isDebugEnabled()){
				log.debug("Accion: " + tipoAccion);
			}
			
			if(tipoAccion==null || tipoAccion.isEmpty()){
				tipoAccion = "desplegar";
			}
			
			RegistroCSV registro=new RegistroCSV();
			if(orden==0)
				orden=1;
			listas(adminDAO);
			switch(tipoAccion) {
				case "regMateria":
					resultado = "regMateria";
					break;
				case "regGenerica":
					resultado = "regGenerica";
					break;
				case "regEspecifica":
					resultado = "regEspecifica";
					break;
				case "insertarGenerica":
					if(adminDAO.insertarGenerica(genericaDTO)==false)
						exito="Se ha registrado correctamente";
					else {
						alerta="Ocurrio un error";
					}
					resultado = "insertarGenerica";
					break;
				case "insertarEspecifica":
					if(adminDAO.insertarEspecifica(especificaDTO, false)==false)
						exito="Se ha registrado correctamente";
					else {
						alerta="Ocurrio un error";
					}
					resultado = "insertarEspecifica";
					break;
				case "listaGenerica":
					resultado = "listaGenerica";
					break;
				case "tablaGenerica":
					listaGenerica=adminDAO.buscarGenerica(nombreCompetencia, orden);
					resultado = "tablaGenerica";
					break;
				case "updateGenerica":
					adminDAO.updateGenerica(genericaDTO);
					resultado = "exito";
					mensajeR="exito";
					break;
				case "deleteGenerica":
					adminDAO.eliminarGenerica(genericaDTO.getIdGenerica());
					resultado = "exito";
					mensajeR="exito";
					break;
				case "listaEspecifica":
					resultado = "listaEspecifica";
					break;
				case "tablaEspecifica":
					listaEspecifica=adminDAO.buscarEspecifica(nombreCompetencia, orden);
					resultado = "tablaEspecifica";
					break;
				case "updateEspecifica":
					adminDAO.updateEspecifica(especificaDTO);
					resultado = "exito";
					mensajeR="exito";
					break;
				case "deleteEspecifica":
					adminDAO.eliminarEspecifica(especificaDTO.getIdEspecifica());
					resultado = "exito";
					mensajeR="exito";
					break;
				case "regCarrera":
					resultado = "regCarrera";
					break;
				case "insertarCarrera":
					resultado = "insertarCarrera";
					if(adminDAO.insertarCarrera(carreraDTO)==false)
						alerta="La materia ya esta registrado";
					else {
						listasF.reset();
						exito="Se ha registrado correctamente";
					}
					break;
				case "regAcademia":
					resultado = "regAcademia";
					break;
				case "regFacultad":
					resultado = "regFacultad";
					break;
				case "insertarAcademia":
					resultado = "insertarAcademia";
					if(adminDAO.insertarAcademia(academiaDTO)==false)
						alerta="La materia ya esta registrado";
					else {
						listasF.reset();
						exito="Se ha registrado correctamente";
					}
						
					break;
				case "insertarFacultad":
					resultado = "insertarFacultad";
					if(adminDAO.insertarFacultad(facultadDTO)==false)
						alerta="La materia ya esta registrado";
					else {
						listasF.reset();
						exito="Se ha registrado correctamente";
					}
						
					break;
				case "carreras":
					resultado = "carreras";
					listaCarrera = adminDAO.obtenerListaCarreras(idFacultad);
					break;
				case "insertarMateria":
					resultado="insertarMateria";
					if(adminDAO.insertarMateria(materiaDTO, idAcademia)==false)
						alerta="La materia ya esta registrado";
					else {
						exito="Se ha registrado correctamente";
						listasF.reset();
					}
					break;
				case "regDocentes":
					resultado="regDocentes";
					if(adminDAO.insertarDocente(docenteDTO, idAcademia, idCarrera, accesoDTO)==false)
						alerta="El docente ya esta registrado";
					else 
						exito="Se ha registrado correctamente";
					break;
				case "docentes":
					resultado="docentes";
					break;
				case "updateDocente":
					adminDAO.updateDocente(docenteDTO);
					resultado="exito";
					mensajeR="exito";
					break;
				case "cargo":
					if(idCargo==4 || idCargo==5){
						listaAcademia=adminDAO.obtenerListaAcademia(idFacultad);
						resultado="academia";
					} else if(idCargo==3) {
						listaCarrera = adminDAO.obtenerListaCarreras(idFacultad);
						resultado="coordinador";
					} else 
						resultado="blanco";
					break;
				case "listaDocentes":
					resultado="listaDocentes";
					break;
				case "cambiarEstadoDocentes":
					adminDAO.cambiarEstadoDocente(accesoDTO);
					resultado="exito";
					mensajeR="exito";
					break;
				case "tablaDocentes":
					listaTipoDocente=adminDAO.buscarDocente(nombreDocente, orden);
					resultado="tablaDocentes";
					break;
				case "eliminarDocente":
					adminDAO.eliminarDocente(docenteDTO.getIdDocente(), docenteDTO.getIdTipo());
					resultado="exito";
					mensajeR="exito";
					break;
				case "generarFacultad":
					resultado="exito";
					adminDAO.insertarDocenteFacultad(docenteDTO.getIdDocente(), idFacultad);
					mensajeR="exito";
					break;
				case "eliminarDocenteFacultad":
					resultado="exito";
					mensajeR="exito";
					adminDAO.eliminarDocenteFacultad(docenteDTO.getIdDocente(), facultadDTO.getFacultad(),docenteDTO.getIdTipo());
					break;
				case "cambiarFacultad":
					resultado="exito";
					mensajeR="exito";
					adminDAO.cambiarDocenteFacultad(docenteDTO.getIdDocente(), Integer.parseInt(alerta), idFacultad);
					break;
				case "infoPuesto":
					docenteDTO=adminDAO.obtenerDocenteById(docenteDTO.getIdDocente());
					resultado="infoPuesto";
					break;
				case "infoAcceso":
					docenteDTO=adminDAO.obtenerDocenteById(docenteDTO.getIdDocente());
					int size=docenteDTO.getListaAcceso().size();
					isAdministrador=docenteDTO.getListaAcceso().get(size-1).getRol().equals("administrador")?true:false;
					resultado="infoAcceso";
					break;
				case "tablaAcceso":
					docenteDTO=adminDAO.obtenerDocenteById(docenteDTO.getIdDocente());
					size=docenteDTO.getListaAcceso().size();
					isAdministrador=docenteDTO.getListaAcceso().get(size-1).getRol().equals("administrador")?true:false;
					resultado="tablaAcceso";
					break;	
				case "updateAcceso":
					resultado="infoAcceso";
					mensajeR="exito";
					break;
				case "addAcceso":
					docenteDTO=adminDAO.obtenerDocenteById(docenteDTO.getIdDocente());
					accesoDTO=new AccesoDTO();
					accesoDTO.setEstado(1);
					accesoDTO.setIdDocente(docenteDTO.getIdDocente());
					accesoDTO.setRol("administrador");
					accesoDTO.setCorreo(docenteDTO.getCorreo());
					accesoDTO.setPass(docenteDTO.getListaAcceso().get(0).getPass());
					adminDAO.insertarAcceso(accesoDTO);
					docenteDTO.setListaAcceso(adminDAO.obtenerListaAccesos(docenteDTO.getIdDocente()));
					resultado="infoAcceso";
					size=docenteDTO.getListaAcceso().size();
					isAdministrador=docenteDTO.getListaAcceso().get(size-1).getRol().equals("administrador")?true:false;
					mensajeR="exito";
					break;
				case "updatePuesto":
					adminDAO.updatePuesto(docenteDTO.getIdDocente(), idFacultad, idCarrera, idAcademia, docenteDTO.getIdTipo(), idCargo);
					resultado="updatePuesto";
					mensajeR="exito";
					break;
				case "eliminarAcceso":
					adminDAO.eliminarAcceso(accesoDTO.getIdAcceso());
					resultado="exito";
					mensajeR="exito";
					break;
				case "listaMaterias":
					resultado="listaMaterias";
					break;
				case "tablaMaterias":
					listaMateria=adminDAO.buscarMateria(nombreMateria, orden);
					resultado="tablaMaterias";
					break;
				case "listaCarreras":
					resultado="listaCarreras";
					break;
				case "tablaCarreras":
					listaCarrera=adminDAO.buscarCarrera(carreraDTO.getNombreCarrera(), orden);
					resultado="tablaCarreras";
					break;
				case "updateCarrera":
					adminDAO.updateCarrera(carreraDTO);
					resultado="exito";
					mensajeR="exito";
					break;
				case "eliminarCarrera":
					adminDAO.eliminarCarrera(carreraDTO.getIdCarrera());
					listasF.reset();
					mensajeR="exito";
					resultado="exito";
					break;
				case "listaCarrerasMaterias":
					resultado="listaCarrerasMaterias";
					break;
				case "tablaCarrerasMaterias":
					listaCarrera=adminDAO.obtenerListaCarrerasMaterias(carreraDTO.getIdCarrera());
					listaMateria=adminDAO.buscarMateria(nombreMateria, orden);
					resultado="tablaCarrerasMaterias";
					break;
				case "asignarMateria":
					adminDAO.asignarMateria(carreraDTO.getIdCarrera(), materiaDTO.getIdMateria(), idCargo);
					resultado="exito";
					mensajeR="exito";
					break;	
				case "listaFacultad":
					resultado="listaFacultad";
					break;
				case "tablaFacultad":
					listaFacultad=adminDAO.buscarFacultad(facultadDTO.getFacultad(), orden);
					resultado="tablaFacultad";
					break;
				case "updateFacultad":
					adminDAO.updateFacultad(facultadDTO);
					resultado="exito";
					mensajeR="exito";
					break;
				case "eliminarFacultad":
					adminDAO.eliminarFacultad(facultadDTO.getIdFacultad());
					listasF.reset();
					resultado="exito";
					mensajeR="exito";
					break;
				case "listaAcademias":
					resultado="listaAcademia";
					break;
				case "tablaAcademia":
					listaAcademia=adminDAO.buscarAcademia(academiaDTO.getNombreAcademia(), orden);
					resultado="tablaAcademia";
					break;
				case "updateAcademia":
					adminDAO.updateAcademia(academiaDTO);
					resultado="exito";
					mensajeR="exito";
					break;
				case "eliminarAcademia":
					adminDAO.eliminarAcademia(academiaDTO.getIdAcademia());
					listasF.reset();
					resultado="exito";
					mensajeR="exito";
					break;
				case "updateMateria":
					adminDAO.updateMateria(materiaDTO);
					resultado="exito";
					mensajeR="exito";
					break;
				case "eliminarMateria":
					adminDAO.eliminarMateria(materiaDTO.getIdMateria());
					resultado="exito";
					listasF.reset();
					mensajeR="exito";
					break;
				case "cambiarAcademia":
					adminDAO.cambiarAcademia(idMateria,idAcademia);
					resultado="exito";
					mensajeR="exito";
					break;
				case "insertarMateriaCSV":
					if(registro.registroCSV(archivo, adminDAO,1)){
						listasF.reset();
						exito=registro.getExito();
					} else {
						alerta=registro.getAlerta();
					}
					resultado="insertarMateriaCSV";
					listasF.reset();
					break;
				case "materiaCSV":
					resultado="materiaCSV";
					break;
				case "insertarDocenteCSV":
					if(registro.registroCSV(archivo, adminDAO,2)){
						listasF.reset();
						exito=registro.getExito();
					} else {
						alerta=registro.getAlerta();
					}
					resultado="insertarDocenteCSV";
					break;
				case "docenteCSV":
					resultado="docenteCSV";
					break;
				case "insertarCarreraCSV":
					if(registro.registroCSV(archivo, adminDAO,3)){
						listasF.reset();
						exito=registro.getExito();
					} else {
						alerta=registro.getAlerta();
					}
					resultado="insertarCarreraCSV";
					break;
				case "carreraCSV":
					resultado="carreraCSV";
					break;
				case "insertarAcademiaCSV":
					if(registro.registroCSV(archivo, adminDAO,4)){
						listasF.reset();
						exito=registro.getExito();
					} else {
						alerta=registro.getAlerta();
					}
					resultado="insertarAcademiaCSV";
					break;
				case "academiaCSV":
					resultado="academiaCSV";
					break;
				case "insertarGenericaCSV":
					if(registro.registroCSV(archivo, adminDAO,5)){
						exito=registro.getExito();
					} else {
						alerta=registro.getAlerta();
					}
					resultado="insertarAcademiaCSV";
					break;
				case "genericaCSV":
					resultado="genericaCSV";
					break;
				case "insertarEspecificaCSV":
					if(registro.registroCSV(archivo, adminDAO,6)) {
						exito=registro.getExito();
					} else {
						alerta=registro.getAlerta();
					}
					mensajeR="exito";
					alerta=registro.getAlerta();
					resultado="insertarAcademiaCSV";
					break;
				case "especificaCSV":
					resultado="especificaCSV";
					break;
				default:
					HttpSession session = ServletActionContext.getRequest().getSession(true);
					docenteDTO = (DocenteDTO) session.getAttribute("docenteDTO");
					if(docenteDTO != null) {
						docenteDTO=adminDAO.obtenerDocenteById(docenteDTO.getIdDocente());
						size=docenteDTO.getListaAcceso().size();
						isAdministrador=docenteDTO.getListaAcceso().get(size-1).getRol().equals("administrador")?true:false;
						if(isAdministrador) {
							resultado="listaDocentes";
						} else {
							resultado="index";
						}
					} else {
						resultado="error";
					}
					
				break;
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
	
	private void listas(AdministradorDAO adminDAO)throws SQLException, DAOException{
		listasF=MySQL_Listas.getInstance(adminDAO);
		listaFacultad=listasF.getListaFacultad();
		listaArea=listasF.getListaArea();
		listaNucleo=listasF.getListaNucleo();
		listaTipo=listasF.getListaTipo();
		listaMateria=listasF.getListaMateria();
		listaAcademia=listasF.getListaAcademia();
		listaTipoDocente=listasF.getListaTipoDocente();
		listaCarrera=listasF.getListaCarrera();
	}

	public boolean getIsAdministrador() {
		return isAdministrador;
	}

	public void setIsAdministrador(boolean isAdministrador) {
		this.isAdministrador = isAdministrador;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public String getRolAcceso() {
		return rolAcceso;
	}

	public void setRolAcceso(String rolAcceso) {
		this.rolAcceso = rolAcceso;
	}

	public AccesoDTO getAccesoDTO() {
		return accesoDTO;
	}

	public void setAccesoDTO(AccesoDTO accesoDTO) {
		this.accesoDTO = accesoDTO;
	}

	public GenericaDTO getGenericaDTO() {
		return genericaDTO;
	}

	public EspecificaDTO getEspecificaDTO() {
		return especificaDTO;
	}

	public List<GenericaDTO> getListaGenerica() {
		return listaGenerica;
	}

	public List<EspecificaDTO> getListaEspecifica() {
		return listaEspecifica;
	}

	public String getNombreCompetencia() {
		return nombreCompetencia;
	}

	public void setNombreCompetencia(String nombreCompetencia) {
		this.nombreCompetencia = nombreCompetencia;
	}

	public void setGenericaDTO(GenericaDTO genericaDTO) {
		this.genericaDTO = genericaDTO;
	}

	public void setEspecificaDTO(EspecificaDTO especificaDTO) {
		this.especificaDTO = especificaDTO;
	}

	public void setListaGenerica(List<GenericaDTO> listaGenerica) {
		this.listaGenerica = listaGenerica;
	}

	public void setListaEspecifica(List<EspecificaDTO> listaEspecifica) {
		this.listaEspecifica = listaEspecifica;
	}

	public AcademiaDTO getAcademiaDTO() {
		return academiaDTO;
	}

	public void setAcademiaDTO(AcademiaDTO academiaDTO) {
		this.academiaDTO = academiaDTO;
	}

	public File getArchivo() {
		return archivo;
	}

	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}

	public List<MateriaDTO> getListaMateria() {
		return listaMateria;
	}

	public void setListaMateria(List<MateriaDTO> listaMateria) {
		this.listaMateria = listaMateria;
	}

	public String getNombreMateria() {
		return nombreMateria;
	}

	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}

	public MateriaDTO getMateriaDTO() {
		return materiaDTO;
	}

	public void setMateriaDTO(MateriaDTO materiaDTO) {
		this.materiaDTO = materiaDTO;
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

	public List<MateriaDTO> getListaNucleo() {
		return listaNucleo;
	}

	public void setListaNucleo(List<MateriaDTO> listaNucleo) {
		this.listaNucleo = listaNucleo;
	}

	public List<MateriaDTO> getListaArea() {
		return listaArea;
	}

	public void setListaArea(List<MateriaDTO> listaArea) {
		this.listaArea = listaArea;
	}

	public List<MateriaDTO> getListaTipo() {
		return listaTipo;
	}

	public void setListaTipo(List<MateriaDTO> listaTipo) {
		this.listaTipo = listaTipo;
	}

	public int getIdFacultad() {
		return idFacultad;
	}

	public void setIdFacultad(int idFacultad) {
		this.idFacultad = idFacultad;
	}

	public int getIdCarrera() {
		return idCarrera;
	}

	public void setIdCarrera(int idCarrera) {
		this.idCarrera = idCarrera;
	}

	public int getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(int idMateria) {
		this.idMateria = idMateria;
	}

	public List<CarreraDTO> getListaCarrera() {
		return listaCarrera;
	}

	public void setListaCarrera(List<CarreraDTO> listaCarrera) {
		this.listaCarrera = listaCarrera;
	}

	public List<FacultadDTO> getListaFacultad() {
		return listaFacultad;
	}

	public void setListaFacultad(List<FacultadDTO> listaFacultad) {
		this.listaFacultad = listaFacultad;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensajeR() {
		return mensajeR;
	}

	public void setMensajeR(String mensajeR) {
		this.mensajeR = mensajeR;
	}

	public DocenteDTO getDocenteDTO() {
		return docenteDTO;
	}

	public void setDocenteDTO(DocenteDTO docenteDTO) {
		this.docenteDTO = docenteDTO;
	}

	public List<DocenteDTO> getListaTipoDocente() {
		return listaTipoDocente;
	}

	public void setListaTipoDocente(List<DocenteDTO> listaTipoDocente) {
		this.listaTipoDocente = listaTipoDocente;
	}
	public List<AcademiaDTO> getListaAcademia() {
		return listaAcademia;
	}

	public void setListaAcademia(List<AcademiaDTO> listaAcademia) {
		this.listaAcademia = listaAcademia;
	}

	public int getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(int idCargo) {
		this.idCargo = idCargo;
	}

	public int getIdAcademia() {
		return idAcademia;
	}

	public void setIdAcademia(int idAcademia) {
		this.idAcademia = idAcademia;
	}
	public String getNombreDocente() {
		return nombreDocente;
	}

	public void setNombreDocente(String nombreDocente) {
		this.nombreDocente = nombreDocente;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	
	public String getExito() {
		return exito;
	}

	public void setExito(String exito) {
		this.exito = exito;
	}

	public String getAlerta() {
		return alerta;
	}

	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}
	public String getTipoAccion() {
		return tipoAccion;
	}

	public void setTipoAccion(String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}
}
