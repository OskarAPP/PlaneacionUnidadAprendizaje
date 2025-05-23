package pua.net.controlador;

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

import pua.net.dao.DAOFactory;
import pua.net.dao.PuaDAO;
import pua.net.dto.DocenteDTO;

public class ProcesarLogin extends ActionSupport implements SessionAware, ServletContextAware{
	
	  private SessionMap<String,Object> sessionMap;
	  private String mensaje;
	  private DocenteDTO docenteDTO;
	  private String usuarioCampo;
	  private String passwordCampo;
	  private DAOFactory daoFactory;
	  private Logger log;
	  private ServletContext servletContext;
	  private PuaDAO puaDAO;
	  
	  public ProcesarLogin() {
		  log = LoggerFactory.getLogger(ProcesarLogin.class);
	  }
	  
	  @Override
	  public void setServletContext(ServletContext context) {
		        this.servletContext = context;
	  }

	  public String execute(){
		  try{
			  
			  if(usuarioCampo != null && passwordCampo != null){
				  
				  daoFactory = DAOFactory.getDAOFactory(
							Integer.parseInt( servletContext.getInitParameter("TIPO") ), 
							(String) servletContext.getInitParameter("DRIVER"), 
							(String) servletContext.getInitParameter("IP"), 
							(String) servletContext.getInitParameter("BD"), 
							(String) servletContext.getInitParameter("USUARIO"), 
							(String) servletContext.getInitParameter("PASS"));
				  	puaDAO = daoFactory.getPuaDAO();
					
				  	docenteDTO = puaDAO.obtenerDocente(usuarioCampo, passwordCampo);
					
					
					
					if(docenteDTO == null){
						mensaje = "Usuario no encontrado o no activo, por favor introduzca correctamente "
								+ "los datos o comuniquese con su administrador";
						return "error";
					}
					
					mensaje = "Usuario encontrado";
					docenteDTO=daoFactory.getAdministradorDAO().obtenerDocenteById(docenteDTO.getIdDocente());
					sessionMap.put("docenteDTO", docenteDTO);
					if(log.isDebugEnabled()){
						log.debug("usuarioCampo:" + usuarioCampo);
						log.debug("passwordCampo:" + passwordCampo);
						log.debug("usuarioID:" + docenteDTO.getIdDocente());
						
					}		
			  }else{
				  
				  return "error";
			  }
			  
		  }catch(Exception e){
			e.printStackTrace();
			mensaje = e.getMessage();
		  }
		  
		  return "exito";
	  }
	
	  public String logout() {
			sessionMap.remove("docenteDTO");
			sessionMap.invalidate();
			ServletActionContext.getRequest().getSession(true).removeAttribute("docenteDTO");
			log.debug("as", sessionMap.get("docenteDTO")==null);
			
			return "logout";
		}
	  
	  public Map<String,Object> getSession() {
	      return sessionMap;
	  }
	
	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = (SessionMap<String, Object>)sessionMap;		
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getUsuarioCampo() {
		return usuarioCampo;
	}

	public void setUsuarioCampo(String usuarioCampo) {
		this.usuarioCampo = usuarioCampo;
	}

	public String getPasswordCampo() {
		return passwordCampo;
	}

	public void setPasswordCampo(String passwordCampo) {
		this.passwordCampo = passwordCampo;
	}

	public DocenteDTO getDocenteDTO() {
		return docenteDTO;
	}

	public void setDocenteDTO(DocenteDTO docenteDTO) {
		this.docenteDTO = docenteDTO;
	}
}
