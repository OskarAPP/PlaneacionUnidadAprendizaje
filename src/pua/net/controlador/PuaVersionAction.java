package pua.net.controlador;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.HibernateException;

import com.opensymphony.xwork2.ActionSupport;

import pua.net.dao.AdministradorDAO;
import pua.net.dao.DAOException;
import pua.net.dao.DAOFactory;
import pua.net.dao.PuaDAO;
import pua.net.dao.PuaVersionDAO;
import pua.net.dao.mysql.MySQL_Listas;
import pua.net.dto.BibliografiaDTO;
import pua.net.dto.DocenteDTO;
import pua.net.dto.EspecificaDTO;
import pua.net.dto.GenericaDTO;
import pua.net.modelo.DownloadPDF;
import pua.hibernate.*;

public class PuaVersionAction extends ActionSupport implements ServletContextAware, SessionAware{
	private ServletContext servletContext;
	private SessionMap<String,Object> session;
	private PuaVersionDAO puaDAO;
	private DAOFactory daoFactory;
	private int idPua;
	private int idPuaVersion;
	private Pua pua;
	private PuaVersion puaVersion;
	private PuaDAO puaManyToMany;
	//private AdministradorDAO adminDAO;
	private List<Pua> listaPua;
	private List<PuaVersion> listaPuaVersion;
	private File archivo;
	private String mensaje;
	private String mensajeR;
	private String tipoAccion;
	private int orden;
	private int planEstudio;
	private String busqueda;
	private MySQL_Listas listasF;
	
	public void setSession(Map<String, Object> arg0) {
		this.session = (SessionMap<String, Object>) session;
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public String execute () {
		String resultado="";
		try {
			daoFactory = DAOFactory.getDAOFactory();
			puaDAO=daoFactory.getPuaVersionDAO();
			if(tipoAccion==null || tipoAccion.isEmpty()){
				tipoAccion = "desplegar";
			}
			listas(puaDAO);
			switch(tipoAccion) {
				case "listaPua":
					resultado="listaPua";
					break;
				case "tablaPua":
					listaPua=puaDAO.getListaPua(busqueda, orden);
					resultado="tablaPua";
					break;
				case "tablaPuaVersion":
					resultado="tablaPuaVersion";
					listaPuaVersion=puaDAO.getListaPuaVersion(busqueda, orden);
					break;
				case "autorizarVersion":
					pua=puaDAO.getPua(idPua);
					pua.setEstado(false);
					pua.setAutorizado(true);
					puaDAO.update(pua);
					puaVersion=new PuaVersion();
					puaVersion.setPua(pua);
					puaVersion.setMateria(pua.getMateria().getNombreMateria());
					puaVersion.setPlanEstudio(pua.getPlanEstudio());
					puaVersion.setPua_1(conversionToByte());
					puaVersion.setVersion(puaDAO.getVersion(pua.getMateria().getNombreMateria()));
					puaVersion.setId(puaDAO.save(puaVersion));
					listasF.resetHibernate();
					resultado="listaPua";
					break;
				case "eliminarPua":
					puaManyToMany=DAOFactory.getDAOFactory(
							Integer.parseInt( servletContext.getInitParameter("TIPO") ), 
							(String) servletContext.getInitParameter("DRIVER"), 
							(String) servletContext.getInitParameter("IP"), 
							(String) servletContext.getInitParameter("BD"), 
							(String) servletContext.getInitParameter("USUARIO"), 
							(String) servletContext.getInitParameter("PASS")).getPuaDAO();
					
					puaDAO.deleteEvaluacion(idPua);
					puaDAO.deleteSubcompetencia(idPua, puaManyToMany);					
					puaDAO.delete(puaDAO.getPua(idPua));
					listasF.resetHibernate();
					resultado="exito";
					mensajeR="exito";
					break;
				case "eliminarVersion":
					puaVersion=puaDAO.getPuaVersion(idPua);
					puaDAO.delete(puaVersion);
					pua=puaVersion.getPua();
					pua.setAutorizado(false);
					puaDAO.update(pua);
					listasF.resetHibernate();
					resultado="exito";
					mensajeR="exito";
					break;
				case "versionamiento":
					pua=puaDAO.getPua(idPua);
					resultado="versionamiento";
					break;
				case "downloadPua":
					System.out.println(idPua);
					new DownloadPDF().exportToPDF(puaDAO.getPuaVersion(idPua));
					resultado="exito";
					break;
				default:
					resultado="listaPuaVersion";
					break;
			}
		} catch(HibernateException e) {
			resultado = "error";
			mensaje = "Ocurrio un error de SQLException al ejecutar la función.";
			mensajeR="fallo";
			e.printStackTrace();	
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			mensaje = "Ocurrio un error de SQLException al ejecutar la función.";
			mensajeR="fallo";
			e.printStackTrace();
		} catch(SQLException e) {
			resultado = "error";
			mensaje = "Ocurrio un error de SQLException al ejecutar la función.";
			mensajeR="fallo";
			e.printStackTrace();	
			
		} 
		System.out.println("Entrada: " + tipoAccion + ", Salida: " + resultado);	
		return resultado;
	}
	
	private void listas(PuaVersionDAO puaDAO) throws NumberFormatException, DAOException, HibernateException {
		listasF=MySQL_Listas.getInstance(puaDAO);
		listaPua=listasF.getListaPua();
		listaPuaVersion=listasF.getListaPuaVersion();
	}
	
	private byte[] conversionToByte() {
		byte[] bytesArray = new byte[(int) archivo.length()];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(archivo);
			fis.read(bytesArray); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return bytesArray;
	}

	public int getPlanEstudio() {
		return planEstudio;
	}

	public void setPlanEstudio(int planEstudio) {
		this.planEstudio = planEstudio;
	}

	public int getIdPuaVersion() {
		return idPuaVersion;
	}

	public void setIdPuaVersion(int idPuaVersion) {
		this.idPuaVersion = idPuaVersion;
	}

	public int getOrden() {
		return orden;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	public File getArchivo() {
		return archivo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public String getMensajeR() {
		return mensajeR;
	}

	public String getTipoAccion() {
		return tipoAccion;
	}

	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public void setMensajeR(String mensajeR) {
		this.mensajeR = mensajeR;
	}

	public void setTipoAccion(String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	public int getIdPua() {
		return idPua;
	}

	public Pua getPua() {
		return pua;
	}

	public PuaVersion getPuaVersion() {
		return puaVersion;
	}

	public List<Pua> getListaPua() {
		return listaPua;
	}

	public List<PuaVersion> getListaPuaVersion() {
		return listaPuaVersion;
	}

	public void setIdPua(int idPua) {
		this.idPua = idPua;
	}

	public void setPua(Pua pua) {
		this.pua = pua;
	}

	public void setPuaVersion(PuaVersion puaVersion) {
		this.puaVersion = puaVersion;
	}

	public void setListaPua(List<Pua> listaPua) {
		this.listaPua = listaPua;
	}

	public void setListaPuaVersion(List<PuaVersion> listaPuaVersion) {
		this.listaPuaVersion = listaPuaVersion;
	}
	
	public ServletContext getServletContext() {
		return servletContext;
	}
}