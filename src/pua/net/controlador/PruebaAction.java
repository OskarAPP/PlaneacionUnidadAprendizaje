package pua.net.controlador;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

import pua.net.dao.DAOException;
import pua.net.dao.DAOFactory;
import pua.net.dao.PuaDAO;
import pua.net.dto.CarreraDTO;
import pua.net.dto.FacultadDTO;
import pua.net.dto.LibroDTO;
import pua.net.dto.PersonaDTO;

public class PruebaAction extends ActionSupport implements ServletContextAware, SessionAware{
	private FacultadDTO facultadDTO;
	private PersonaDTO personaDTO;
	private DAOFactory daoFactory;
	private LibroDTO librosDTO; 
	private List<LibroDTO> listaLibros;
	private PuaDAO puaDAO;
	private String mensaje;
	private String tipoAccion;
	private Logger log;
	private ServletContext servletContext;
	
	
	
	public PruebaAction() {
		// TODO Auto-generated constructor stub
		log = LoggerFactory.getLogger(PruebaAction.class);
	}
	
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public String execute(){
		String resultado = "exito";
		mensaje = "";
		
		try{
			
			if(log.isDebugEnabled()){
				log.debug("tipoAccion="+tipoAccion);
			}
			
			daoFactory = DAOFactory.getDAOFactory(
					Integer.parseInt( servletContext.getInitParameter("TIPO") ), 
					(String) servletContext.getInitParameter("DRIVER"), 
					(String) servletContext.getInitParameter("IP"), 
					(String) servletContext.getInitParameter("BD"), 
					(String) servletContext.getInitParameter("USUARIO"), 
					(String) servletContext.getInitParameter("PASS"));
			puaDAO = daoFactory.getPuaDAO();
			
		
			listaLibros= puaDAO.obtenerListaLibros();
		
		}catch(SQLException e){
			resultado = "error";
			mensaje = "Ocurrio un error de SQLException al ejecutar la función.";
			e.printStackTrace();			
		}catch(DAOException e){
			resultado = "error";
			mensaje = "Ocurrio un error de DAOException al intentar conectarse a la BD.";
			e.printStackTrace();
		}
		
		return resultado;
	}

	public FacultadDTO getFacultadDTO() {
		return facultadDTO;
	}

	public void setFacultadDTO(FacultadDTO facultadDTO) {
		this.facultadDTO = facultadDTO;
	}
	
	public PersonaDTO getPersonaDTO() {
		return personaDTO;
	}

	public void setPersonaDTO(PersonaDTO personaDTO) {
		this.personaDTO = personaDTO;
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
	
	public List<LibroDTO> getListaLibros() {
		return listaLibros;
	}

	public void setListaLibros(List<LibroDTO> listaLibros) {
		this.listaLibros= listaLibros;
	}

}
